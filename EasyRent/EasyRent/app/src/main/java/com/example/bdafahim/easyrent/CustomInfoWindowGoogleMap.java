package com.example.bdafahim.easyrent;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap  implements GoogleMap.InfoWindowAdapter{
    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView type = view.findViewById(R.id.custom_id);
        TextView rentfee = view.findViewById(R.id.custom_rentfee);
        TextView owner = view.findViewById(R.id.custom_name);
        TextView email = view.findViewById(R.id.custom_email);
        TextView phone = view.findViewById(R.id.custom_phone);
        TextView road = view.findViewById(R.id.custom_road_house);
        TextView area = view.findViewById(R.id.custom_area);

        type.setText(marker.getTitle());
        rentfee.setText(marker.getSnippet());

        Rent_Add rent_add = (Rent_Add)marker.getTag();

        owner.setText((String)rent_add.getOwner_name());
        email.setText((String)rent_add.getEmail());
        phone.setText((String)rent_add.getPhone_no());
        area.setText((String)rent_add.getArea());
        String temp = "House_No: "+rent_add.getHouse_no()+" Road_No: "+rent_add.getRoad_no();
        road.setText(temp);
        return view;
    }
}
