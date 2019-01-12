package com.example.bdafahim.easyrent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Owner_Login extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private EditText username,password;
    private Button login,signup;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__login);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.owner_signin);
        username = findViewById(R.id.owner_email);
        password = findViewById(R.id.owner_password);

        progressDialog = new ProgressDialog(this);

        signup = findViewById(R.id.owner_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Owner_Login.this,"Login Pressed",Toast.LENGTH_SHORT).show();
                LOGIN_PROCESS();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Owner_Login.this,SignUp.class);
                startActivity(intent);
                Toast.makeText(Owner_Login.this,"Signup Clicked",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void LOGIN_PROCESS(){
        String EmailL = username.getText().toString().trim();
        String PasswordL = password.getText().toString().trim();

        if(TextUtils.isEmpty(EmailL)){
            //Toast.makeText(User_Login.this,"Please Enter your Email",Toast.LENGTH_SHORT).show();
            username.setError("Email Required");
            username.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(PasswordL) || PasswordL.length()<6){
            //Toast.makeText(User_Login.this,"Please Enter your Password",Toast.LENGTH_SHORT).show();
            password.setError("Valid password needed");
            password.requestFocus();
            return;
        }

        progressDialog.setMessage("Log in is in progress");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(EmailL,PasswordL)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            progressDialog.dismiss();
                            Toast.makeText(Owner_Login.this,"User Login Successful",Toast.LENGTH_SHORT)
                                    .show();
                            startActivity(new Intent(getApplicationContext(),OwnerProfile.class));
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Owner_Login.this,task.getException().getMessage(),Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
    /*@Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }*/
}
