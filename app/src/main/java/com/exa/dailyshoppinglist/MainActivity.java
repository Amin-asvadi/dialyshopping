package com.exa.dailyshoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText EmailLogin, PassLogin;
    Button login;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmailLogin = findViewById(R.id.userEditText);
        PassLogin = findViewById(R.id.passEditText);
        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.btnlogin);
            mDialog = new ProgressDialog(MainActivity.this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = EmailLogin.getText().toString().trim();
                String mPass = PassLogin.getText().toString().trim();
                if (TextUtils.isEmpty(mEmail)) {

                    EmailLogin.setError("complete filed");
                    return;
                }
                if (TextUtils.isEmpty(mPass)) {
                    PassLogin.setError("complete field");
                    return;
                }
                mDialog.setMessage("In progress");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "sucsess", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    startActivity(new Intent(MainActivity.this,Home_Activity.class));


                }
                else {
                    Toast.makeText(MainActivity.this, "user or pass wrong", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
                    }
                });
            }
        });


    }


    public void signup(View view) {
        Intent intent = new Intent(MainActivity.this, Register_activity.class);
        startActivity(intent);
    }
}
