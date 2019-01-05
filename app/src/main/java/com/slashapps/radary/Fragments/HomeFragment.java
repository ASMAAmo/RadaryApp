package com.slashapps.radary.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slashapps.radary.Activities.HomeActivity;
import com.slashapps.radary.Adapters.PlaceAutocompleteAdapter;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.Presenters.AllCamsPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.AllCamsView;
import com.slashapps.radary.WebService.Models.MyPlaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.slashapps.radary.Activities.HomeActivity.updateDevice;
import static java.security.AccessController.getContext;


public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener ,AllCamsView {
    View v;
    public static GoogleMap map;
    GoogleApiClient mpiclients,googleclient;
    Location mlastLocation;
    LocationRequest mLocationrequest;
    GeoFire geoFire;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    AllCamsPresenter presenter;
    AutoCompleteTextView ed_search;
    private PlaceAutocompleteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleapiClient();
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ed_search = (AutoCompleteTextView) v.findViewById(R.id.search_view);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/geofire");
        geoFire = new GeoFire(ref);
        presenter = new AllCamsPresenter(getActivity(), HomeFragment.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        //
        presenter.getMyplaces();
        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationrequest = new LocationRequest();
        mLocationrequest.setInterval(60000);
        mLocationrequest.setFastestInterval(60000);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mpiclients, place.getId());
              //  placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mlastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        geoFire.setLocation("firebase-hq", new GeoLocation(location.getLatitude(), -location.getLongitude()));
        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
        Map<String, String> deviceInfo = new HashMap();
        deviceInfo.put("Lat", location.getLatitude() + "");
        deviceInfo.put("Long", location.getLongitude() + "");
        deviceInfo.put("NotificationsToken", SessionHelper.getNotificationsToken(mFusedLocationProviderClient.getApplicationContext()));
        deviceInfo.put("OS", "Android " + Build.MODEL + Build.MANUFACTURER + " Ver : " + Build.VERSION.RELEASE);
        updateDevice(mFusedLocationProviderClient.getApplicationContext(), deviceInfo);

        //Invoke service with every location check
        // presenter.getMyplaces();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // set night mode
        if (HomeActivity.pageIndex == 3) {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_in_night));
        }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        getDeviceLocation();
        inite();
    }
  /*  private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = adapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mpiclients, placeId);
           // placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };*/
    private void inite() {
       // Log.d(TAG, "init: initializing");


       // ed_search.setOnItemClickListener(mAutocompleteClickListener);

        adapter = new PlaceAutocompleteAdapter(getActivity(), HomeActivity.googleclient,
                LAT_LNG_BOUNDS, null);

        ed_search.setAdapter(adapter);
        //
        ed_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideSoftKeyboard();
                startGooglemap();

            }
        });
        /* AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideSoftKeyboard();
                // native
               *//* final AutocompletePrediction item = adapter.getItem(i);
                final String placeId = item.getPlaceId();

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(HomeActivity.googleclient, placeId);*//*
               // placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                // google Api

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intent);
            }
        };*/

        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

    }
    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        String searchString = ed_search.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

           moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }
    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            map.addMarker(options);
        }

        hideSoftKeyboard();
    }
    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 5f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    protected synchronized void buildGoogleapiClient() {
        mpiclients = new GoogleApiClient.Builder(getActivity())
              //  .addConnectionCallbacks(this).enableAutoManage(getActivity(), 1, this)
                        .addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API)
                //.addApi(Places.PLACE_DETECTION_API)
                //.enableAutoManage(getActivity(), this)
                .addApi(LocationServices.API).build();
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
                                        5);
                                map.moveCamera(update);
                                Map<String,String> deviceInfo =new HashMap();
                                deviceInfo.put("Lat",location.getLatitude()+"");
                                deviceInfo.put("Long",location.getLongitude()+"");
                                deviceInfo.put("NotificationsToken",SessionHelper.getNotificationsToken(mFusedLocationProviderClient.getApplicationContext()));
                                deviceInfo.put("OS","Android "+ Build.MODEL + Build.MANUFACTURER +" Ver : "+ Build.VERSION.RELEASE);
                                updateDevice(mFusedLocationProviderClient.getApplicationContext(),deviceInfo);
                            }

                        }
                    }
                });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void getAllcams(){
    }


    @Override
    public void getAllCams(List<MyPlaces> data) {

        //We will work here
        for (int i=0;i<data.size();i++){
            if (data.get(i).getCamTypeId().equals("1")){

                LatLng latLng = new LatLng(Double.parseDouble(data.get(i).getLat()),Double.parseDouble( data.get(i).getLng()));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markeroffice));
                markerOptions.getPosition();
                map.addMarker(markerOptions);

            }else {
                LatLng latLng = new LatLng(Double.parseDouble(data.get(i).getLat()),Double.parseDouble( data.get(i).getLng()));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markeroffice));
                markerOptions.getPosition();
                map.addMarker(markerOptions);
            }
        }


    }

    @Override
    public void onError(String err) {

    }
    public void startGooglemap(){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
        startActivity(intent);
    }
}
