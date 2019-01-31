package com.example.bdafahim.easyrent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABShape;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class profile_user2 extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{

    private CircleImageView imageViewb;
    private TextView sname,semail,saddress,sphone;
    private DatabaseReference mdata;
    private static User_Info uinfo ;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    private String EMAIL,url,lati,longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        final ProgressDialog Dialog = new ProgressDialog(profile_user2.this);
        Dialog.setMessage("Loading ...");
        Dialog.show();

        lati = getIntent().getExtras().getString("Lati").toString();
        longi = getIntent().getExtras().getString("Longi").toString();;

        imageViewb = findViewById(R.id.imageView);
        sname = findViewById(R.id.show_name);
        saddress = findViewById(R.id.show_address);
        sphone = findViewById(R.id.show_phone);
        semail = findViewById(R.id.show_email);

        mdata = FirebaseDatabase.getInstance().getReference("Users");
        rfaLayout = (RapidFloatingActionLayout) findViewById(R.id.activity_main_rfal);
        rfaBtn = (RapidFloatingActionButton) findViewById(R.id.activity_main_rfab);


        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(profile_user2.this);
        List<RFACLabelItem> items = new ArrayList<>();


        items.add(new RFACLabelItem<Integer>()
                .setLabel("Update Profile")
                .setResId(R.drawable.circle_ba)
                .setIconNormalColor(0xff64b5f6)
                .setIconPressedColor(0xffd7ccc8)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Search")
//                        .setResId(R.mipmap.ico_test_c)
                        .setDrawable(getResources().getDrawable(R.drawable.circle_ba))
                        .setIconNormalColor(0xff64b5f6)
                        .setIconPressedColor(0xff3e2723)
                        .setLabelSizeSp(14)
                        .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Logout")
                .setResId(R.drawable.circle_ba)
                .setIconNormalColor(0xff64b5f6)
                .setIconPressedColor(0xff0d5302)
                .setWrapper(2)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(RFABTextUtil.dip2px(this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(RFABTextUtil.dip2px(this, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();


        mdata.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        url = dataSnapshot.child("imageString").getValue(String.class).toString();
                        //System.out.println(url);
                        uinfo = dataSnapshot.getValue(User_Info.class);

                        //uinfo.pri();
                        Picasso.get().load(url).fit().into(imageViewb);
                        //System.out.println("Naem" +uinfo.getImageString());
                        sname.setText("Name : "+uinfo.getUname());
                        saddress.setText("Address : "+uinfo.getUaddress());
                        sphone.setText("Phone : "+uinfo.getUphoneNo());
                        semail.setText("Email : "+uinfo.getUemail());
                        EMAIL = uinfo.getUemail();
                        Dialog.dismiss();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        Toast.makeText(profile_user2.this, "clicked label: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        if(position==0)
        {
            Intent i = new Intent(profile_user2.this,profile_update.class);
            i.putExtra("email",EMAIL);
            i.putExtra("URL",url);
            startActivity(i);
        }
        else if(position==1)
        {
            Intent i = new Intent(profile_user2.this,Map_activity.class);
            i.putExtra("Lati",lati);
            i.putExtra("Longi",longi);

            startActivity(i);
            //startActivity(new Intent(profile_user2.this,Search_Add.class));
        }
        else if(position==2)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(profile_user2.this,UserCatagory.class));
        }

        rfabHelper.toggleContent();
    }
}
