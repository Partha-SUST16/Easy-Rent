package com.example.bdafahim.easyrent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.R.layout.*;

public class register extends AppCompatActivity {

    ///initializing variables
    private Button signup;
    private EditText iname,iaddress,iphone,imail,ipass;
    private String username,useradd,userphone,usermail,userpass;
    private ImageView iimg;
    private static final int Gallery_Reques = 1;
    private Uri SelectImgaeUri = null;

    private DatabaseReference mdata=FirebaseDatabase.getInstance().getReference().child("Users");
    private StorageReference mstorage=FirebaseStorage.getInstance().getReference("Users");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iname = findViewById(R.id.registername);
        iimg = findViewById(R.id.registerImage);
        iaddress = findViewById(R.id.registerAddress);
        iphone = findViewById(R.id.registerPhoneNo);
        imail = findViewById(R.id.registermail);
        ipass = findViewById(R.id.registerpassword);
        signup = findViewById(R.id.registersign_up_button);


        iimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Reques);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupProcess();
            }
        });
    }

    private void SignupProcess()
    {
        username = iname.getText().toString().trim();
        useradd  = iaddress.getText().toString().trim();
        userphone = iphone.getText().toString().trim();
        usermail = imail.getText().toString().trim();
        userpass = ipass.getText().toString().trim();

        if(username.isEmpty()){
            iname.setError("Name ccan't be empty");
            iname.requestFocus();
            return;
        }
        if(useradd.isEmpty()){
            iaddress.setError("Address ccan't be empty");
            iaddress.requestFocus();
            return;
        }
        if(userphone.isEmpty()){
            iphone.setError("Name ccan't be empty");
            iphone.requestFocus();
            return;
        }
        if(usermail.isEmpty()){
            imail.setError("Name ccan't be empty");
            imail.requestFocus();
            return;
        }
        if(userpass.isEmpty()){
            ipass.setError("Name ccan't be empty");
            ipass.requestFocus();
            return;
        }

        final ProgressDialog Dialog = new ProgressDialog(register.this);
        Dialog.setMessage("Creating Account...");
        Dialog.show();

        mAuth.createUserWithEmailAndPassword(usermail,userpass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startPosting();
                            Dialog.dismiss();
                            finish();
                        }else {
                            Toast.makeText(register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Dialog.dismiss();
                        }
                    }
                });
    }

    public void startPosting()
    {
        if(SelectImgaeUri != null)
        {

            StorageReference filePath = mstorage.child(mAuth.getCurrentUser().getUid().toString()).child(SelectImgaeUri.getLastPathSegment());

            filePath.putFile(SelectImgaeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    //String UName, String uaddress, String uphoneNo, String uemail, String UImg
                    User_Info user_info = new User_Info(username,useradd,userphone,usermail,downloadUri.toString());
                    Log.d("TESTING : ",user_info.getImageString());
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user_info);
                    Log.d("TESTING : ",user_info.getImageString());
                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Reques && resultCode == RESULT_OK)
        {
            SelectImgaeUri = data.getData();
            iimg.setImageURI(SelectImgaeUri);
        }
    }
}
