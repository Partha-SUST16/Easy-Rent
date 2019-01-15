package com.example.bdafahim.easyrent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import java.util.Map;

public class OwnerProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "ViewOwnerInformation";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;

    private ProgressDialog progressDialog;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        mListView = (ListView) findViewById(R.id.listviewO);
        mDrawer = findViewById(R.id.drawer_owner);
        progressDialog = new ProgressDialog(this);

        //toogle button
        mToggle = new ActionBarDrawerToggle(OwnerProfile.this,mDrawer,R.string.open,R.string.close);
        mToggle.syncState();
        mDrawer.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListner();

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        progressDialog.setMessage("Please wait for a moment..");
        progressDialog.show();

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
               // Toast.makeText(OwnerProfile.this,"Inside Datasnapshot1",Toast.LENGTH_SHORT).show();
                showData(dataSnapshot);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    //for support action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
           // Toast.makeText(OwnerProfile.this,"Inside Datasnapshot 2",Toast.LENGTH_SHORT).show();

            User_Information uinfo = new User_Information();
            Rent_Add rent = new Rent_Add();


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
            array.add("Name    : "+uinfo.getUName());
            array.add("Email   : "+uinfo.getUemail());
            array.add("PhoneNo : "+uinfo.getUphoneNo());
            array.add("Address : "+uinfo.getUaddress());



            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,array);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        switch (menuItem.getItemId()) {

            case R.id.postAdd: {
                //do somthing
                startActivity(new Intent(OwnerProfile.this,Post_Add.class));
                toastMessage("PostAdd Button clicked");
                break;
            }
            case R.id.managePost:{
                toastMessage("Manage Post Clicked");
                startActivity(new Intent(OwnerProfile.this,Manage_Post.class));
                break;
            }
            case R.id.LogoutO:{
                toastMessage("Logout clicked");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(OwnerProfile.this,UserCatagory.class));
                break;
            }
        }
        //close navigation drawer

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewO);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
