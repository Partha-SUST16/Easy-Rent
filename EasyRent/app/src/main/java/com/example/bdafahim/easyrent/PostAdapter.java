package com.example.bdafahim.easyrent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Context mCtx;
    private List<Rent_Add> driverList;

    public PostAdapter(Context mCtx, List<Rent_Add> driverList) {
        this.mCtx = mCtx;
        this.driverList = driverList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyleviewadapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Rent_Add ra =  driverList.get(i);

        myViewHolder.rent_txt.setText(Integer.toString(ra.getFee()));
        myViewHolder.type_txt.setText(ra.getType());
        myViewHolder.phone_txt.setText(ra.getPhone_no());
        myViewHolder.email_txt.setText(ra.getEmail());
        myViewHolder.area_text.setText(ra.getArea());
        myViewHolder.name_txt.setText(ra.getOwner_name());
        myViewHolder.key_val.setText(ra.getKey());
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

      TextView type_txt,rent_txt,area_text,phone_txt,email_txt,name_txt,key_val;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type_txt = itemView.findViewById(R.id.type_id);
            rent_txt = itemView.findViewById(R.id.rent_feeid);
            area_text = itemView.findViewById(R.id.rent_areaid);
            phone_txt = itemView.findViewById(R.id.phone_rentid);
            email_txt = itemView.findViewById(R.id.emal_rentid);
            name_txt =itemView.findViewById(R.id.renter_nameid);
            key_val = itemView.findViewById(R.id.rent_key);
            // textViewCountry = itemView.findViewById(R.id.text_view_country);
        }
    }
}
