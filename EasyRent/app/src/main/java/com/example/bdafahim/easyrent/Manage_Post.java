package com.example.bdafahim.easyrent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Manage_Post extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List addList;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__post);
        recyclerView = findViewById(R.id.recyclerView);
        addList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this,addList);
        recyclerView.setAdapter(postAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Posts");
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(valueEventListener);

        //PROCESS();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            addList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Rent_Add driver = snapshot.getValue(Rent_Add.class);
                    Log.d("ManagePost",driver.getOwner_name());
                    Log.d("ManagePost",driver.getOwner_name());
                    Log.d("ManagePost",driver.getOwner_name());
                    Log.d("ManagePost",driver.getOwner_name());
                   // Toast.makeText(Manage_Post.this,"Hi 1 ",Toast.LENGTH_LONG).show();
                    addList.add(driver);
                }
                postAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };
}
