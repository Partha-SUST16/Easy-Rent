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
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity
{

    private EditText editName,editPassword,editEmail,editAddress,editPhone_no;
    private Button signUpButton;
    private ProgressDialog progressDialog;
    private  String name,password,email,address,phone;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        editName = findViewById(R.id.name_id);
        editAddress = findViewById(R.id.address_id);
        editEmail = findViewById(R.id.email_id);
        editPassword = findViewById(R.id.password_id);
        editPhone_no = findViewById(R.id.phone_id);

        signUpButton = findViewById(R.id.resitration);

        progressDialog = new ProgressDialog(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resisterUser();
            }
        });
    }

    private void resisterUser()
    {
        name = editName.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        phone = editPhone_no.getText().toString().trim();
        address = editAddress.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            editEmail.setError("Email Can't be Empty");
            editEmail.requestFocus();
            return;

        }

        if(TextUtils.isEmpty(password)){
            editPassword.setError("Password Can't be Empty");
            editPassword.requestFocus();
            return;

        }
        if (password.length() < 6) {
            editPassword.setError("Minimum Password length 6");
            editPassword.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editPhone_no.setError("phone can't be empty");
            editPhone_no.requestFocus();
            return;
        }

        if (phone.length() != 11) {
            editPhone_no.setError("Enter valid Phone no");
            editPhone_no.requestFocus();
            return;
        }
        if(address.isEmpty())
        {
            editAddress.setError("Address ccan't be empty");
            editAddress.requestFocus();
            return;
        }
        if(name.isEmpty())
        {
            editName.setError("Name ccan't be empty");
            editName.requestFocus();
            return;
        }
        progressDialog.setMessage("Registration in process...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();

                            User_Info user = new User_Info(
                                    /*name,address,
                                    phone,email*/
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> ttask) {

                                    if (ttask.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "Resistration Successful", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        //display a failure message
                                        Toast.makeText(SignUp.this,ttask.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            //startActivity(new Intent(getApplicationContext(),UserCatagory.class));
                            //Toast.makeText(SignUp.this,"Registered Succeessful",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();                        }

                    }
                });

    }
}
