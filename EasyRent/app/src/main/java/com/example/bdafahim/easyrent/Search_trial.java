package com.example.bdafahim.easyrent;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Search_trial extends FragmentActivity implements OnMapReadyCallback {

    private String user_location,search_location;
    private GoogleMap mMap;
    ArrayList<Rent_Add> user_arrayList = new ArrayList<>();
    DatabaseReference mDatabase;
    Button map_button;
   private String phone,email,owner,type,road;
   private int fee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trial);

        Intent intent = getIntent();
        user_location = intent.getStringExtra("user_location").toString().trim();

        Log.d("Inside MAin",user_location);
        Log.d("Inside MAin",user_location);
        Log.d("Inside MAin",user_location);
        Log.d("Inside MAin",user_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users")
                .child("POSTS");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Rent_Add rent_add = dataSnapshot.getValue(Rent_Add.class);
                Geocoder geocoder = new Geocoder(Search_trial.this);
                List<Address>addressList = null;

                MarkerOptions markerOptions = new MarkerOptions();

                search_location = rent_add.getArea();
                owner = rent_add.getOwner_name();
                email = rent_add.getEmail();
                phone = rent_add.getPhone_no();
                type = rent_add.getType();
                fee = rent_add.getFee();
                road = rent_add.getRoad_no();
                LatLng test = new LatLng(rent_add.getLati(),rent_add.getLongi());

                try {
                    addressList = geocoder.getFromLocationName(search_location,6);
                    if(addressList!=null)
                    {
                        for (int i=0;i<addressList.size();i++)
                        {
                            Address userAddress = addressList.get(i);

                            LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());


                            Log.d("Inside Database",latLng.toString());
                            markerOptions.position(latLng);
                            markerOptions.title(type);
                            markerOptions.snippet(Integer.toString(fee));

                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(Search_trial.this);
                            mMap.setInfoWindowAdapter(customInfoWindow);
                            Marker m = mMap.addMarker(markerOptions);
                            m.setTag(rent_add);
                            m.showInfoWindow();
                            //mMap.addMarker(markerOptions);

                        }

                    }

                }catch (Exception e){
                    Toast.makeText(Search_trial.this,e.getMessage(),Toast.LENGTH_LONG).show();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(Search_trial.this);
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        CircleOptions circleOptions = new CircleOptions();
        mMap = googleMap;
        try{
            addressList = geocoder.getFromLocationName(user_location,10);

            if(addressList!=null)
            {
                for (int i=0;i<addressList.size();i++)
                {
                    Address userAddress = addressList.get(i);

                    LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                    Log.d("Inside Onready",latLng.toString());
                    Log.d("Inside Onready",latLng.toString());
                    Log.d("Inside Onready",latLng.toString());
                    Log.d("Inside Onready",latLng.toString());

                    markerOptions.position(latLng);
                    markerOptions.title(user_location);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                    circleOptions.center(latLng);
                    circleOptions.radius(10000);
                    circleOptions.strokeColor(Color.CYAN);
                    circleOptions.fillColor(0x4D000080);

                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    mMap.addCircle(circleOptions);


                }

            }

        }catch (Exception e){
            Toast.makeText(Search_trial.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
