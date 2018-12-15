package com.example.bdafahim.easyrent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Map;

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

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Posts");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectData((Map<String,Object>)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void collectData(Map<String,Object> users)
    {
        ArrayList<String> array = new ArrayList<>();
        for(Map.Entry<String,Object>entry:users.entrySet()){
            Map singleUser = (Map) entry.getValue();

           email.setText((String)singleUser.get("email"));
           owner.setText((String)singleUser.get("owner_name"));
           phone.setText((String)singleUser.get("phone_no"));
           long temp = (Long)singleUser.get("fee");
          // int ree = Integer.parseInt(temp);
           fee.setText(Long.toString(temp));
            area.setText((String)singleUser.get("area"));
            road.setText((String)singleUser.get("road_no"));
            type.setText((String)singleUser.get("type"));
           double temp1 = (Double)singleUser.get("lati");
           lati.setText(Double.toString(temp1));
            temp1 = (Double)singleUser.get("longi");
            longi.setText(Double.toString(temp1));
            Log.d("UpdateDelete",Long.toString(temp));
            Log.d("UpdateDelete",Double.toString(temp1));
            Log.d("UpdateDelete",Double.toString(temp1));
            Log.d("UpdateDelete",Double.toString(temp1));
            Log.d("UpdateDelete",Double.toString(temp1));
            house.setText((String)singleUser.get("house_no"));
        }


    }
    private void PROCESS2(){
        String house_no, road_no, address, phone_no, email_no,ownername,type1;
        double lati1,longi1;
        int rentfee;
        house_no = house.getText().toString().trim();
        road_no = road.getText().toString().trim();
        address = area.getText().toString().trim();
        phone_no=phone.getText().toString().trim();
        email_no=email.getText().toString().trim();
        ownername=owner.getText().toString().trim();
        type1=type.getText().toString().trim();
        rentfee=Integer.parseInt(fee.getText().toString());
        lati1 = Double.parseDouble(lati.getText().toString());
        longi1=Double.parseDouble(longi.getText().toString());
        Rent_Add rent_add = new Rent_Add(house_no,ownername,road_no,address,phone_no,email_no,type1,rentfee,lati1,longi1,key);
        mRef.setValue(rent_add).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateDelete.this,"Update Successfull",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(UpdateDelete.this,task.getException().toString(),Toast.LENGTH_LONG).show();
            }
        })   ;
    }
}
