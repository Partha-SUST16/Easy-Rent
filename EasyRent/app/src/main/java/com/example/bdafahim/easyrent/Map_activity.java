package com.example.bdafahim.easyrent;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import java.io.IOException;
import java.util.List;

public class Map_activity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private double lati,longi;
    private String Lat,Lone,area,type,radius,location,test;

    private EditText harea,hardius,htype;
    private ImageButton search;

    private Address hostAddress;
    private LatLng t_latLng;

    private GoogleMap uMap;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_activity);

        Lat = getIntent().getExtras().getString("Lati").toString();
        Lone = getIntent().getExtras().getString("Longi").toString();
        Log.d("LAtLong :",Lat);
        Log.d("LAtLong :",Lone);

        hardius = findViewById(R.id.radias);
        harea = findViewById(R.id.house_area);
        search = findViewById(R.id.search_id);
        type="";

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
        final ProgressDialog Dialog = new ProgressDialog(Map_activity.this);
        Dialog.setMessage("Loading ...");


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.show();
                work();
                Dialog.dismiss();
            }
        });

        Spinner spinner = findViewById(R.id.flatTypeId);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.houseTypeArray,R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }

    private void work(){
        area = harea.getText().toString().trim();
        radius = hardius.getText().toString().trim();

        if(area.isEmpty()){
            harea.setError("Enter Area");
            harea.requestFocus();
            return;
        }
        if(test.isEmpty())
            test = "Single Seat";
        radius = hardius.getText().toString().trim();
        if(radius.isEmpty())
            radius = "10";

        CircleOptions circleOptions = new CircleOptions();
        MarkerOptions markerOptions = new MarkerOptions();

        PRE();
        circleOptions.center(t_latLng);
        circleOptions.radius(Integer.valueOf(radius) * 1000);
        circleOptions.strokeColor(Color.CYAN);
        circleOptions.fillColor(0x4D000080);
        uMap.addCircle(circleOptions);


        /////////////////////////////////////////////////
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users")
                .child("POSTS");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Rent_Add rent_add = dataSnapshot.getValue(Rent_Add.class);

                //debugging
                Log.d("Testing ",rent_add.getArea());
                Log.d("Testing ",rent_add.getType());
                Log.d("Testing ",type);
                Log.d("Testing ","..........");

                Geocoder geocoder = new Geocoder(Map_activity.this);
                List<Address>addressList = null;
                MarkerOptions markerOptions = new MarkerOptions();
                //checking if rent_add object is equal to null
                assert rent_add!=null;
                location = rent_add.getArea();
                if(rent_add.getType().equalsIgnoreCase(test)){
                    try {
                        addressList = geocoder.getFromLocationName(location, 6);
                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {

                                Address userAddress = addressList.get(i);

                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                //getting distance between two place
                                float results[] = new float[100];
                                Location.distanceBetween(userAddress.getLatitude(), userAddress.getLongitude(), hostAddress.getLatitude(), hostAddress.getLongitude(), results);

                                Log.d("Testing ",Float.toString(results[0]));
                                Log.d("Testing ",Float.toString(results[0]));
                                //convert result into kilometer
                                if (results[0] / 1000 <= Float.valueOf(radius)){
                                    markerOptions.position(latLng);
                                    markerOptions.title(rent_add.getType());
                                    markerOptions.snippet(Integer.toString(rent_add.getFee()));



                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                                    CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(Map_activity.this);
                                    uMap.setInfoWindowAdapter(customInfoWindow);
                                    Marker m = uMap.addMarker(markerOptions);
                                    m.setTag(rent_add);
                                    m.showInfoWindow();
                                }
                            }
                        }
                    }catch (Exception e) {
                        Toast.makeText(Map_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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


    private void PRE()
    {
        uMap.clear();
        Geocoder geocoder = new Geocoder(Map_activity.this);
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();

        try {
            addressList = geocoder.getFromLocationName(area,10);
            if(addressList!=null)
            {
                for (int i=0;i<addressList.size();i++)
                {
                    hostAddress = addressList.get(i);

                    t_latLng = new LatLng(hostAddress.getLatitude(),hostAddress.getLongitude());

                    //adding marker to searched place

                    markerOptions.position(t_latLng);
                    markerOptions.title(area);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    //uMap.addMarker(markerOptions);
                    uMap.moveCamera(CameraUpdateFactory.newLatLngZoom(t_latLng,10));

                }

                //addressList.add(addressList.get(0));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("LAtLong On:",Lat);
        Log.d("LAtLong On:",Lone);
        lati = Double.parseDouble(Lat);
        longi = Double.parseDouble(Lone);

        MarkerOptions markerOptions = new MarkerOptions();
        uMap = googleMap;
        LatLng temp = new LatLng(lati,longi);
        markerOptions.position(temp);
        markerOptions.title("Your Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        uMap.addMarker(markerOptions);
        uMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp,10));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
           test = parent.getItemAtPosition(pos).toString();
           Toast.makeText(Map_activity.this,test,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
