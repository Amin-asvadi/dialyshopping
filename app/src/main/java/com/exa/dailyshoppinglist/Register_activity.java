package com.exa.dailyshoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_activity extends AppCompatActivity {

    EditText reguser,regpass;
    Button btnreg;
    private FirebaseAuth Auth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        reguser = findViewById(R.id.reguserEditText);
        regpass = findViewById(R.id.regpassEditText);
        btnreg = findViewById(R.id.btnreg);
        Auth = FirebaseAuth.getInstance();



        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String muser = reguser.getText().toString().trim();
                String mpass = regpass.getText().toString().trim();
                mDialog = new ProgressDialog(Register_activity.this);
                if (TextUtils.isEmpty(muser)){

                    reguser.setError("Complete Filed");
                    return;
                }
                if(TextUtils.isEmpty(mpass)){
                    regpass.setError("Complete filed");
                    return;
                }
                mDialog.setMessage("In Progress...");
                mDialog.show();

                Auth.createUserWithEmailAndPassword(muser,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register_activity.this, "sucsess", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            startActivity(new Intent(Register_activity.this,MainActivity.class));
                       
                            
                        }
                        else {
                            Toast.makeText(Register_activity.this, "have a problem", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });

            }
        });

    }

    public void signin(View view) {
        finish();
    }
}
