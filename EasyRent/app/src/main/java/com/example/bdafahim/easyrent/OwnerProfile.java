package com.example.bdafahim.easyrent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerProfile extends AppCompatActivity {

    private static final String TAG = "ViewOwnerInformation";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        mListView = (ListView) findViewById(R.id.listviewO);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Toast.makeText(OwnerProfile.this,"Inside Datasnapshot1",Toast.LENGTH_SHORT).show();
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Toast.makeText(OwnerProfile.this,"Inside Datasnapshot 2",Toast.LENGTH_SHORT).show();

            User_Information uinfo = new User_Information();
            uinfo.setUName(ds.child(userID).getValue(User_Information.class).getUName());
            uinfo.setUemail(ds.child(userID).getValue(User_Information.class).getUemail());
            uinfo.setUphoneNo(ds.child(userID).getValue(User_Information.class).getUphoneNo());
            uinfo.setUaddress(ds.child(userID).getValue(User_Information.class).getUaddress());

            //display all the information
            Log.d(TAG, "showData: name: " + uinfo.getUName());
            Log.d(TAG, "showData: email: " + uinfo.getUemail());
            Log.d(TAG, "showData: phone_num: " + uinfo.getUphoneNo());
            Log.d(TAG,"showData: address: "+uinfo.getUaddress());

            ArrayList<String> array = new ArrayList<>();
            array.add(uinfo.getUName());
            array.add(uinfo.getUemail());
            array.add(uinfo.getUphoneNo());
            array.add(uinfo.getUaddress());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);
        }
    }




    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
