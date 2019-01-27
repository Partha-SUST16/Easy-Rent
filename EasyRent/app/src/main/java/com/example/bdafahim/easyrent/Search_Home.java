package com.example.bdafahim.easyrent;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Search_Home extends FragmentActivity implements OnMapReadyCallback {

    private String user_location,type,radius,location;
    private ImageButton imageButton;

    private GoogleMap mMap,uMap;
    Address hostAddress;
    private EditText search_radius,search_type;
    ArrayList<Rent_Add> user_arrayList = new ArrayList<>();
    List<Address> distances = new ArrayList<>();
    LatLng t_latLng;

    DatabaseReference mDatabase;
    Button map_button;
    private String phone,email,owner,road;
    private int fee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__home);

        ///getting value from previous intent
        Intent intent = getIntent();
        user_location = intent.getStringExtra("user_location").toString().trim();
        Log.d("Testing  " ,user_location);

        //find button according to their id
        search_type = (EditText) findViewById(R.id.search_pet);
        imageButton = (ImageButton) findViewById(R.id.search_imageButton);
        search_radius = (EditText)findViewById(R.id.search_radius);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        //setting onclicklisterner to imgButton

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleOptions circleOptions = new CircleOptions();
                MarkerOptions markerOptions = new MarkerOptions();

                //getting radius from searchbox
                radius = search_radius.getText().toString().trim();
                if(radius.isEmpty())
                    radius = "10";

                mMap.clear();
                //arrayList.removeAll(arrayList);


                //adding marker to searched place
                markerOptions.position(t_latLng);
                markerOptions.title(user_location);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.addMarker(markerOptions);

                //creating circle around search place
                circleOptions.center(t_latLng);
                circleOptions.radius(Integer.valueOf(radius)*1000);
                circleOptions.strokeColor(Color.CYAN);
                circleOptions.fillColor(0x4D000080);
                mMap.addCircle(circleOptions);


                type = search_type.getText().toString();
                if(type.equalsIgnoreCase("Full Flat") || type=="full flat")
                    type = "Full Flat";
                else if(type.equalsIgnoreCase("Single Seat") || type=="single seat")
                    type="Single Seat";
                else
                    type="Single Seat";

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child("POSTS");
                mDatabase.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Rent_Add rent_add = dataSnapshot.getValue(Rent_Add.class);

                        Log.d("Testing ",rent_add.getArea());
                        Log.d("Testing ",rent_add.getType());
                        Log.d("Testing ",type);
                        Log.d("Testing ","..........");

                        Geocoder geocoder = new Geocoder(Search_Home.this);
                        List<Address>addressList = null;
                        MarkerOptions markerOptions = new MarkerOptions();
                       // assert rent_add!=null;
                        location = rent_add.getArea();
                        if(rent_add.getType().equalsIgnoreCase(type)){
                            try {
                                addressList = geocoder.getFromLocationName(location, 6);
                                if (addressList != null) {
                                    for (int i = 0; i < addressList.size(); i++) {

                                        Address userAddress = addressList.get(i);

                                        LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                        distances.add(addressList.get(0));
                                        float results[] = new float[100];
                                        Location.distanceBetween(userAddress.getLatitude(), userAddress.getLongitude(), hostAddress.getLatitude(), hostAddress.getLongitude(), results);
                                        Log.d("Testing ",Float.toString(results[0]));
                                        Log.d("Testing ",Float.toString(results[0]));
                                        if (results[0] / 1000 <= Float.valueOf(radius)){
                                            markerOptions.position(latLng);
                                            markerOptions.title(rent_add.getType());
                                            markerOptions.snippet(Integer.toString(rent_add.getFee()));



                                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                                            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(Search_Home.this);
                                            mMap.setInfoWindowAdapter(customInfoWindow);
                                            Marker m = mMap.addMarker(markerOptions);
                                            m.setTag(rent_add);
                                            m.showInfoWindow();
                                        }
                                    }
                                }
                            }catch (Exception e) {
                                Toast.makeText(Search_Home.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(Search_Home.this);
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();

    mMap = googleMap;
        try{
            addressList = geocoder.getFromLocationName(user_location,10);

            Log.d("Testing ",Double.toString(addressList.get(0).getLatitude()));
            if(addressList!=null)
            {
                for (int i=0;i<addressList.size();i++)
                {
                    hostAddress = addressList.get(i);

                    t_latLng = new LatLng(hostAddress.getLatitude(),hostAddress.getLongitude());

                    markerOptions.position(t_latLng);
                    markerOptions.title(user_location);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(t_latLng,10));

                }

                //addressList.add(addressList.get(0));

            }

        }catch (Exception e){
            Toast.makeText(Search_Home.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            Log.d("Testing ",e.getMessage().toString());
        }
    }
}
