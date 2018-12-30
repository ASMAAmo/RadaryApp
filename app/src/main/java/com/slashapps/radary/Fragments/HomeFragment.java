package com.slashapps.radary.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slashapps.radary.Activities.HomeActivity;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.slashapps.radary.Activities.HomeActivity.updateDevice;
import static java.security.AccessController.getContext;


public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    View v;
    public static GoogleMap map;
    GoogleApiClient mpiclients;
    Location mlastLocation;
    LocationRequest mLocationrequest;
    GeoFire geoFire;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/geofire");
        geoFire = new GeoFire(ref);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        // Inflate the layout for this fragment
        return v;


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationrequest = new LocationRequest();
        mLocationrequest.setInterval(1000);
        mLocationrequest.setFastestInterval(1000);
        mLocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mpiclients, mLocationrequest, HomeFragment.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mlastLocation= location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        geoFire.setLocation("firebase-hq", new GeoLocation(location.getLatitude(), -location.getLongitude()));
        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

        Map<String,String> deviceInfo =new HashMap();
        deviceInfo.put("Lat",location.getLatitude()+"");
        deviceInfo.put("Long",location.getLongitude()+"");
        updateDevice(mFusedLocationProviderClient.getApplicationContext(),deviceInfo);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // set night mode
        if(HomeActivity.pageIndex==3){
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_in_night));
        }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        buildGoogleapiClient();
        getDeviceLocation();
    }

    protected synchronized void buildGoogleapiClient() {
        mpiclients = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mpiclients.connect();


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
                                LatLng currentLatLng = new LatLng(location.getLatitude(),
                                        location.getLongitude());
                                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,
                                        15);
                                map.moveCamera(update);
                                Map<String,String> deviceInfo =new HashMap();
                                deviceInfo.put("Lat",location.getLatitude()+"");
                                deviceInfo.put("Long",location.getLongitude()+"");
                                updateDevice(mFusedLocationProviderClient.getApplicationContext(),deviceInfo);
                            }

                        }
                    }
                });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }



}
