package com.example.bdafahim.easyrent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class profile_update extends AppCompatActivity {

    private EditText uname,uphone,uadd;
    private ImageView imgView;
    private Button update_btn;
    private static User_Info uinfo ;
    private Uri SelectImgaeUri = null;
    private String Email,URL;

    private static final int Gallery_Reques = 1;

    private DatabaseReference mData;
    private StorageReference mstorage=FirebaseStorage.getInstance().getReference("Users");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        Email = getIntent().getExtras().getString("email").toString();
        URL = getIntent().getExtras().getString("URL").toString();
        uname = findViewById(R.id.updatename);
        uphone = findViewById(R.id.updatephone);
        uadd = findViewById(R.id.updateaddress);
        imgView = findViewById(R.id.imageupdate);
        update_btn = findViewById(R.id.update_profile);

        final ProgressDialog Dialog = new ProgressDialog(profile_update.this);
        Dialog.setMessage("Loading...");
        Dialog.show();

        mData = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        mAuth = FirebaseAuth.getInstance();
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String url = dataSnapshot.child("imageString").getValue(String.class).toString();
                //System.out.println(url);
                uinfo = dataSnapshot.getValue(User_Info.class);

                //uinfo.pri();
                Picasso.get().load(url).fit().into(imgView);
                //System.out.println("Naem" +uinfo.getImageString());
                uname.setText(uinfo.getUname());
                uadd.setText(uinfo.getUaddress());
                uphone.setText(uinfo.getUphoneNo());
                Dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Reques);
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.show();
                //Toast.makeText(profile_update.this,"Pressed",Toast.LENGTH_SHORT).show();
                startPosting();
                Dialog.dismiss();
                Toast.makeText(profile_update.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(profile_update.this));
            }
        });


    }

    private void startPosting()
    {
        if(SelectImgaeUri != null)
        {
            StorageReference filePath = mstorage.child(mAuth.getCurrentUser().getUid().toString()).child(SelectImgaeUri.getLastPathSegment());

            filePath.putFile(SelectImgaeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    //String UName, String uaddress, String uphoneNo, String uemail, String UImg
                    User_Info user_info = new User_Info(uname.getText().toString().trim(),
                            uadd.getText().toString().trim(),
                            uphone.getText().toString().trim(),Email,downloadUri.toString());
                    Log.d("TESTING : ",user_info.getImageString());
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user_info);
                    Log.d("TESTING : ",user_info.getImageString());
                }
            });
        }
        else
        {
            User_Info user_info = new User_Info(uname.getText().toString().trim(),
                    uadd.getText().toString().trim(),
                    uphone.getText().toString().trim(),Email,URL);
            Log.d("TESTING : ",user_info.getImageString());
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user_info);
            Log.d("TESTING : ",user_info.getImageString());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Reques && resultCode == RESULT_OK)
        {
            SelectImgaeUri = data.getData();
            imgView.setImageURI(SelectImgaeUri);
        }
    }
}
