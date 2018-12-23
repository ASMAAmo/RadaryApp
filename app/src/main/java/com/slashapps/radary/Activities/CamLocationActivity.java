package com.slashapps.radary.Activities;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.slashapps.radary.R;

public class CamLocationActivity extends BaseActivity implements OnMapReadyCallback , View.OnClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private RippleView getLocationBtn;
    static LatLng currentLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this);
        getLocationBtn=(RippleView)findViewById(R.id.rel_select_location);
        getLocationBtn.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
       getDeviceLocation();
    }

    private void getDeviceLocation() {
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location location = task.getResult();
                        if(location!=null){
                            // Add a marker in Sydney and move the camera
                            currentLatLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,
                                    15);
                            mMap.moveCamera(update);
                            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                                @Override
                                public void onCameraIdle() {
                                    //get latlng at the center by calling
                                    currentLatLng = mMap.getCameraPosition().target;
                                }
                            });
                        }

                    }
                }
            });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
       int view =v.getId();
       switch (view){
           case R.id.rel_select_location :

               HomeActivity.camLat=currentLatLng.latitude+"";
               HomeActivity.camLng=currentLatLng.longitude+"";
               CamLocationActivity.this.finish();
             //  Toast.makeText(CamLocationActivity.this,currentLatLng.latitude+" "+currentLatLng.longitude,Toast.LENGTH_LONG).show();
               break;
       }

    }
}
