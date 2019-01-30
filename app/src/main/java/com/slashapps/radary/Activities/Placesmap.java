package com.slashapps.radary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slashapps.radary.R;

public class Placesmap extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
    public static GoogleMap map;
    GoogleApiClient mpiclients;
    String lat,lang,camid,title;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placesmap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lat = "";
        lang="";
        intent = getIntent();
        lat= intent.getStringExtra("lat");
        lang= intent.getStringExtra("lang");
        camid=intent.getStringExtra("camId");
        //
        //

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lang));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(title));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lang)), 12.0f));
       // Log.e("latlang",lat+" "+lang+"");
    }

    @Override
    public void onClick(View v) {
        switch (camid){

            case "1" :{
                title=getResources().getString(R.string.trafficcam);
                break;
            }
            case "2" :{
                title=getResources().getString(R.string.securityambush);
                break;
            }
        }
    }
}
