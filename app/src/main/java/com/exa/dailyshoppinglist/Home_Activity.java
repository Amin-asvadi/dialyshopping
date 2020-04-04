package com.exa.dailyshoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;

public class Home_Activity extends AppCompatActivity {
    Button btnadd;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    TextView totalamont;


    private String type;
    private int amount;
    private String note;
    private String post_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("shpping list").child(uId);
        mDatabase.keepSynced(true);
        recyclerView = findViewById(R.id.recyclerview);
        totalamont = findViewById(R.id.totalAmount);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        btnadd = findViewById(R.id.addproduct);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                int totalammont = 0;
                for (DataSnapshot snap:dataSnapshot.getChildren()){
                    Data data = snap.getValue(Data.class);
                    totalammont += data.getAmount();
                    String sttotal = String.valueOf(totalammont);
                    totalamont.setText(sttotal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customdialog();

            }
        });
    }

    private void customdialog() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(Home_Activity.this);
        LayoutInflater inflater = LayoutInflater.from(Home_Activity.this);
        View myview = inflater.inflate(R.layout.input_data, null);
        final AlertDialog dialog = myDialog.create();
        dialog.setView(myview);

        dialog.show();

        final EditText product = myview.findViewById(R.id.productedittext);
        final EditText price = myview.findViewById(R.id.priceedittext);
        final EditText note = myview.findViewById(R.id.noteedit);
        Button save = myview.findViewById(R.id.save_value);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String mproduct = product.getText().toString().trim();
                String mprice = price.getText().toString().trim();
                String mnote = note.getText().toString().trim();
                int priceint = Integer.parseInt(mprice);
                if (TextUtils.isEmpty(mproduct)) {

                    product.setError("مقداری را وارد نمایید");

                }
                if (TextUtils.isEmpty(mprice)) {

                    product.setError("مقداری را وارد نمایید");
                }
                String id = mDatabase.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(mproduct, priceint, mnote, date, id);
                mDatabase.child(id).setValue(data);

                Toast.makeText(Home_Activity.this, "کالای خریداری شده ثبت شد", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(
                Data.class,
                R.layout.item_data,
                MyViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder, final Data data, final int i) {
                myViewHolder.setDate(data.getDate());
                myViewHolder.setammount(data.getAmount());
                myViewHolder.setType(data.getType());
                myViewHolder.setnote(data.getNote());
                myViewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_key = getRef(i).getKey();
                        type = data.getType();
                        note = data.getNote();
                        amount = data.getAmount();
                        Log.i("value offffff",type);Log.i("value offffff",note);Log.i("value offffff",String.valueOf(amount));

                        updateData();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public void updateData(){

        AlertDialog.Builder mydialog= new AlertDialog.Builder(Home_Activity.this);
        LayoutInflater inflater = LayoutInflater.from(Home_Activity.this);
        View mView = inflater.inflate(R.layout.update_field,null);
        final AlertDialog dialog = mydialog.create();
        dialog.setView(mView);
        final EditText editType = mView.findViewById(R.id.edit_productedit_text);
        final EditText editPrice_ammount =mView.findViewById(R.id.edit_price_edit_text);
        final EditText editnote = mView.findViewById(R.id.edit_note_edit);

        editType.setText(type);
        editType.setSelection(type.length());


        editPrice_ammount.setText(String.valueOf(amount));
        editPrice_ammount.setSelection(String.valueOf(amount).length());

        editnote.setText(note);
        editnote.setSelection(note.length());

        Button btnupdate = mView.findViewById(R.id.btn_update_value);
        Button btndelet = mView.findViewById(R.id.btn_del_valiue);


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = editType.getText().toString().trim();
                String mammount = String.valueOf(amount);
                mammount=editPrice_ammount.getText().toString().trim();
                note = editnote.getText().toString().trim();
                int intammount = Integer.parseInt(mammount);
                String date = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(type,intammount,note,date,post_key);
                mDatabase.child(post_key).setValue(data);
                dialog.dismiss();
            }
        });
        btndelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(post_key).removeValue();
                dialog.dismiss();
            }
        });




        dialog.show();


    }

}
