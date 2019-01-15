package com.example.bdafahim.easyrent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
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


    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
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

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                String title1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.rent_key)).getText().toString();
                Toast.makeText(Manage_Post.this, "Single Click on position :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                String title1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.rent_key)).getText().toString();
                Intent i = new Intent(Manage_Post.this,UpdateDelete.class);
                i.putExtra("ref",title1);
                startActivity(i);

            }

        }));

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
