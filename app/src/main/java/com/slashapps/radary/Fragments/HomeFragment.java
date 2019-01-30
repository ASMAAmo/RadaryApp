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
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
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
import com.slashapps.radary.ConstantClasss.GPSManager;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.Presenters.AllCamsPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.AllCamsView;
import com.slashapps.radary.ViewsInterfaces.GPSCallback;
import com.slashapps.radary.WebService.Models.MyPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
import static com.slashapps.radary.Activities.HomeActivity.googleclient;
import static com.slashapps.radary.Activities.HomeActivity.updateDevice;
import static java.security.AccessController.getContext;


public class HomeFragment extends Fragment implements GPSCallback, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener ,AllCamsView {
    View v;
    public static GoogleMap map;
    GoogleApiClient mpiclients;
    Location mlastLocation;
    TextView txtspeed;
    Float speed;
    LocationRequest mLocationrequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    AllCamsPresenter presenter;
    AutoCompleteTextView ed_search;
    private PlaceAutocompleteAdapter adapter;
    com.google.android.gms.ads.AdView mAdView;
    InterstitialAd mInterstitialAd;
    private GPSManager gpsManager = null;
    Boolean isGPSEnabled=false;
    LocationManager locationManager;
    double currentSpeed,kmphSpeed;
    ImageView icdot;
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
        ed_search =  v.findViewById(R.id.search_view);
        icdot=(ImageView)v.findViewById(R.id.icdot);
        txtspeed=(TextView)v.findViewById(R.id.txtspeed);
        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" +
                "ca-app-pub-3940256099942544/6300978111");
        speed=0.0f;
        try {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //

        presenter = new AllCamsPresenter(getActivity(), HomeFragment.this);
        presenter.getMyplaces();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        initViews();
        getCurrentSpeed();
        return v;
    }
    public void getCurrentSpeed(){
       // txtview.setText(getString(R.string.info));
        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        gpsManager = new GPSManager(getActivity());
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled) {
            gpsManager.startListening(getActivity());
            gpsManager.setGPSCallback(this);
        } else {
            gpsManager.showSettingsAlert();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationrequest = new LocationRequest();
        mLocationrequest.setInterval(60000);
        mLocationrequest.setFastestInterval(60000);
        mLocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLocationChanged(Location location) {
        mlastLocation= location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // Zoom in the Google Map
         speed= location.getSpeed();
        Log.e("speed",speed+"");
       // Toast.makeText(getActivity(),speed.toString(),Toast.LENGTH_LONG).show();
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
        Map<String,String> deviceInfo =new HashMap();
        deviceInfo.put("Lat",location.getLatitude()+"");
        deviceInfo.put("Long",location.getLongitude()+"");
        deviceInfo.put("speed",location.getSpeed()+"");
        deviceInfo.put("NotificationsToken",SessionHelper.getNotificationsToken(mFusedLocationProviderClient.getApplicationContext()));
        deviceInfo.put("OS","Android "+ Build.MODEL + Build.MANUFACTURER +" Ver : "+ Build.VERSION.RELEASE);
        updateDevice(mFusedLocationProviderClient.getApplicationContext(),deviceInfo);
       // getCurrentSpeed();
        txtspeed.setText(speed.toString());

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
        txtspeed.setText(speed.toString());
    }

    protected synchronized void buildGoogleapiClient() {
        mpiclients = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).addApi(Places.GEO_DATA_API).build();
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
                            deviceInfo.put("speed",location.getSpeed()+"");
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

    @Override
    public void getAllCams(List<MyPlaces> data) {
        //We will work here
        if(!data.isEmpty()){
        for (int i=0;i<data.size();i++) {
            if (!data.get(i).getActive().equals("0")) {
                if (data.get(i).getCamTypeId().equals("1")) {
                    LatLng latLng = new LatLng(Double.parseDouble(data.get(i).getLat()), Double.parseDouble(data.get(i).getLng()));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_markeroffice));
                    markerOptions.getPosition();
                    map.addMarker(markerOptions);
                } else {
                    LatLng latLng = new LatLng(Double.parseDouble(data.get(i).getLat()), Double.parseDouble(data.get(i).getLng()));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_police));
                    markerOptions.getPosition();
                    map.addMarker(markerOptions);
                }
            }
        }
        }
    }

    @Override
    public void onError(String err) {

    }



    public void initViews(){
        adapter = new PlaceAutocompleteAdapter(getActivity(), HomeActivity.googleclient,
                null, null);
        ed_search.setAdapter(adapter);
        ed_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Geocoder selected_place_geocoder = new Geocoder(getContext());
                    List<Address> address;

                    address = selected_place_geocoder.getFromLocationName(ed_search.getText().toString(), 5);

                    if (address == null) {

                    } else {
                        Address location = address.get(0);

                        Uri navigationIntentUri = Uri.parse("google.navigation:q=" + location.getLatitude() +"," + location.getLongitude());//creating intent with latlng
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    fetchLatLongFromService fetch_latlng_from_service_abc = new fetchLatLongFromService(
                            ed_search.getText().toString().replaceAll("\\s+", ""));
                    fetch_latlng_from_service_abc.execute();

                }



            }
        });

    }
    public static double round(double unrounded, int precision, int roundingMode) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }
    @Override
    public void onGPSUpdate(Location location) {
        speed = location.getSpeed();
        currentSpeed = round(speed,3, BigDecimal.ROUND_HALF_UP);
        kmphSpeed = round((currentSpeed*3.6),3,BigDecimal.ROUND_HALF_UP);
       // txtspeed.setText( String.valueOf(kmphSpeed));

    }


    //Get Lat long from Address
    public class fetchLatLongFromService extends
            AsyncTask<Void, Void, StringBuilder> {
        String place;


        public fetchLatLongFromService(String place) {
            super();
            this.place = place;

        }


        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
            this.cancel(true);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                HttpURLConnection conn = null;
                StringBuilder jsonResults = new StringBuilder();
                String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
                        + this.place + "&sensor=false";

                URL url = new URL(googleMapUrl);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                String a = "";
                return jsonResults;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                JSONObject jsonObj;
                jsonObj = new JSONObject(result.toString());
                JSONArray resultJsonArray = jsonObj.getJSONArray("results");

                // Extract the Place descriptions from the results
                // resultList = new ArrayList<String>(resultJsonArray.length());

                JSONObject before_geometry_jsonObj = resultJsonArray
                        .getJSONObject(0);

                JSONObject geometry_jsonObj = before_geometry_jsonObj
                        .getJSONObject("geometry");

                JSONObject location_jsonObj = geometry_jsonObj
                        .getJSONObject("location");

                String lat_helper = location_jsonObj.getString("lat");
                double lat = Double.valueOf(lat_helper);


                String lng_helper = location_jsonObj.getString("lng");
                double lng = Double.valueOf(lng_helper);


                LatLng point = new LatLng(lat, lng);


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
