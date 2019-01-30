package com.example.bdafahim.easyrent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class User_Login extends AppCompatActivity {


    private GpsTracker gpsTracker;
    private double lati,longi;

    private Button login,signup;
    private EditText emailText,passwordText;
    //private Button signin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailText = findViewById(R.id.user_email);
        passwordText = findViewById(R.id.user_password);

        progressDialog = new ProgressDialog(this);

        login = findViewById(R.id.user_signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation(view);
                LOGIN_PROCESS();
            }
        });
        signup = findViewById(R.id.user_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(User_Login.this,"signup clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Login.this,register.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }

    void LOGIN_PROCESS(){


        String EmailL = emailText.getText().toString().trim();
        String PasswordL = passwordText.getText().toString().trim();

        if(TextUtils.isEmpty(EmailL)){
            //Toast.makeText(User_Login.this,"Please Enter your Email",Toast.LENGTH_SHORT).show();
            emailText.setError("Email Required");
            emailText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(PasswordL) || PasswordL.length()<6){
            //Toast.makeText(User_Login.this,"Please Enter your Password",Toast.LENGTH_SHORT).show();
            passwordText.setError("Valid password needed");
            passwordText.requestFocus();
            return;
        }

        progressDialog.setMessage("Login is in progress");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(EmailL,PasswordL)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            progressDialog.dismiss();
                            Toast.makeText(User_Login.this,"User Login Successful",Toast.LENGTH_SHORT)
                                    .show();
                            Intent i = new Intent(getApplicationContext(),profile_user2.class);

                            i.putExtra("Lati",Double.toString(lati));
                            i.putExtra("Longi",Double.toString(longi));
                            startActivity(i);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(User_Login.this,task.getException().getMessage(),Toast.LENGTH_LONG)
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

    public void getLocation(View view){
        gpsTracker = new GpsTracker(User_Login.this);
        if(gpsTracker.canGetLocation()){
            lati = gpsTracker.getLatitude();
            longi= gpsTracker.getLongitude();

        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}
