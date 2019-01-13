package com.example.bdafahim.easyrent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserCatagory extends AppCompatActivity implements View.OnClickListener {

    Button renter,houseOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_catagory);

        renter = findViewById(R.id.need_house);
        houseOwner = findViewById(R.id.house_owner);

        renter.setOnClickListener(this);
        houseOwner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == renter)
        {
            Intent intent = new Intent(UserCatagory.this,User_Login.class);
            startActivity(intent);
        }
        if(view == houseOwner){
            Intent intent = new Intent(UserCatagory.this,Owner_Login.class);
            startActivity(intent);

        }
    }
}
