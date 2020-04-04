package com.exa.dailyshoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class Home_Activity extends AppCompatActivity {
    Button btnadd;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser mUser =mAuth.getCurrentUser();
            String uId = mUser.getUid();
           mDatabase = FirebaseDatabase.getInstance().getReference().child("shpping list").child(uId);
           mDatabase.keepSynced(true);
           recyclerView =findViewById(R.id.recyclerview);

           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
           linearLayoutManager.setStackFromEnd(true);
           linearLayoutManager.setReverseLayout(true);
           recyclerView.setHasFixedSize(true);
           recyclerView.setLayoutManager(linearLayoutManager);

        btnadd = findViewById(R.id.addproduct);

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
        View myview = inflater.inflate(R.layout.input_data,null);
final AlertDialog dialog = myDialog.create();
            dialog.setView(myview);

dialog.show();

        final EditText product = myview.findViewById(R.id.productedittext);
        final EditText  price =myview.findViewById(R.id.priceedittext);
        final EditText  note =myview.findViewById(R.id.noteedit);
        Button save =myview.findViewById(R.id.save_value);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String mproduct = product.getText().toString().trim();
                String mprice = price.getText().toString().trim();
                String mnote = note.getText().toString().trim();
                int priceint = Integer.parseInt(mprice);
                if (TextUtils.isEmpty(mproduct)){

                    product.setError("مقداری را وارد نمایید");

                }
                if (TextUtils.isEmpty(mprice)){

                    product.setError("مقداری را وارد نمایید");
                }
                String id = mDatabase.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(mproduct,priceint,mnote,date,id);
                mDatabase.child(id).setValue(data);

                Toast.makeText(Home_Activity.this ,"کالای خریداری شده ثبت شد",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });




    }
}
