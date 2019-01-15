package com.example.bdafahim.easyrent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search_Add extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private EditText node;
    private Button search_btn;
    private List addList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__add);

        node = findViewById(R.id.search_text);
        search_btn = findViewById(R.id.search_b);


        try {
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PRE2();
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(Search_Add.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void PRE() {
        String place = search_btn.getText().toString().trim();
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").child("POSTS")
                .orderByChild("area").startAt(place.toUpperCase()).endAt(place.toLowerCase()+"\uf8ff");

        query.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            addList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Rent_Add driver = snapshot.getValue(Rent_Add.class);
                    addList.add(driver);

                }
                postAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private void PRE2()
    {
        if(node.getText().toString().isEmpty())
        {
            node.setError("Place can't be empty ");
            node.requestFocus();

            return;
        }
        Intent intent = new Intent(Search_Add.this,Search_trial.class);
        intent.putExtra("user_location",node.getText().toString().trim());
        startActivity(intent);
    }


}