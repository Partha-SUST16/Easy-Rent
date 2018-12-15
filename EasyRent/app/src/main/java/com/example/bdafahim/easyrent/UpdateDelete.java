package com.example.bdafahim.easyrent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDelete extends AppCompatActivity {

    private Button btn_update,btn_delete;
    private EditText type,owner,house,road,area,phone,email,fee,lati,longi;
    private String key;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        key = getIntent().getExtras().getString("ref").toString();
        type = findViewById(R.id.utype);
        owner = findViewById(R.id.uowner);
        house = findViewById(R.id.urent_house);
        road = findViewById(R.id.urent_road);
        area = findViewById(R.id.urent_area);
        phone = findViewById(R.id.urent_phone);
        email = findViewById(R.id.urent_email);
        fee = findViewById(R.id.urent_fee);
        lati = findViewById(R.id.ulati);
        longi = findViewById(R.id.ulongi);

       // PROCESS1();
        btn_update = findViewById(R.id.update_btn);
        btn_delete = findViewById(R.id.delete_btn);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Posts").child(key);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdateDelete.this,"Post delete SuccessFull",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(UpdateDelete.this,task.getException().toString(),Toast.LENGTH_LONG).show();

                    }
                });
                finish();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PROCESS2();
            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Toast.makeText(OwnerProfile.this,"Inside Datasnapshot1",Toast.LENGTH_SHORT).show();
                PROCESS1(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void PROCESS1(DataSnapshot dataSnapshot){

    }
    private void PROCESS2(){

    }
}
