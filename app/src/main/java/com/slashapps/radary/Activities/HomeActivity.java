package com.slashapps.radary.Activities;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.MapView;
import com.slashapps.radary.BuildConfig;
import com.slashapps.radary.Components.BottomNavigation;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.Fragments.AddCamFragment;
import com.slashapps.radary.Fragments.HomeFragment;
import com.slashapps.radary.Fragments.MoreFragment;
import com.slashapps.radary.Fragments.AddedLocationsFragment;
import com.slashapps.radary.Fragments.NightModeFragment;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener ,GoogleApiClient.OnConnectionFailedListener{
    private static final int REQUEST_READ_PHONE_STATE=5;
    private BottomNavigation navigation;
    private static final int HOME = 0;
    private static final int MYLOCATION = 1;
    private static final int ADDCAMERA = 2;
    private static final int NIGHTMODE = 3;
    private static final int MOREPAGE=4;
    public static int pageIndex = 0;
    public static String placeName="";
    public static Double placeLat=0.0;
    public static Double placeLang=0.0;
    public static String camLat="";
    public static String camLng="";
    private TextView title;
    public static Toolbar myToolbar;
  public static   GoogleApiClient googleclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        navigation = findViewById(R.id.navigation);
        title = findViewById(R.id.title);
        navigation.setSelectedItemId(R.id.navigation_home);
///
        googleclient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(HomeActivity.this,this)
                .build();
        Log.e("Test Notification Token",SessionHelper.getNotificationsToken(HomeActivity.this));
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        if (googleclient != null && googleclient.isConnected()) {
            googleclient.stopAutoManage(HomeActivity.this);
            googleclient.disconnect();
        }

    }*/

    @Override
    public void onStop() {
        super.onStop();
        if (googleclient != null && googleclient.isConnected()) {
            googleclient.stopAutoManage(HomeActivity.this);
            googleclient.disconnect();
        }
    }

    private void switchToPage(int page) {
        FragmentTransaction transaction = getTransaction();
        switch (page) {
            case HOME:
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.home));
                transaction.replace(R.id.content, new HomeFragment()).addToBackStack("0");
                transaction.commit();
                pageIndex = 0;
                break;
            case MYLOCATION:
                if (!SessionHelper.isLogin(HomeActivity.this)){
                    Toast.makeText(HomeActivity.this,getResources().getString(R.string.pleaselogin),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HomeActivity.this,Loginactivity.class);
                    startActivity(intent);
                }else {
                    myToolbar.setVisibility(View.VISIBLE);
                    title.setText(this.getResources().getString(R.string.mylocation));
                    transaction.replace(R.id.content, new AddedLocationsFragment()).addToBackStack("0");
                    transaction.commit();
                    pageIndex = 1;
                }
                break;
            case ADDCAMERA:
                if (!SessionHelper.isLogin(HomeActivity.this)){
                    Toast.makeText(HomeActivity.this,getResources().getString(R.string.pleaselogin),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HomeActivity.this,Loginactivity.class);
                    startActivity(intent);
                }else {
                    myToolbar.setVisibility(View.GONE);
                    transaction.replace(R.id.content, new AddCamFragment()).addToBackStack("0");
                    transaction.commit();
                    pageIndex = 2;
                }

                break;
            case NIGHTMODE:
                pageIndex = 3;
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.nightmode));
                transaction.replace(R.id.content, new HomeFragment()).addToBackStack("0");
                transaction.commit();
                break;

            case MOREPAGE:
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.more));
               // startActivity(new Intent(this,Addmobs.class));
                transaction.replace(R.id.content, new MoreFragment()).addToBackStack("0");
                transaction.commit();
                pageIndex = 4;
                break;
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_home) {
            switchToPage(HOME);
            pageIndex=0;
            return true;
        } else if (id == R.id.navigation_my_location) {
            switchToPage(MYLOCATION);
           // pageIndex=1;
            return true;
        } else if (id == R.id.navigation_add_camera) {
            switchToPage(ADDCAMERA);
            pageIndex=2;
            return true;
        } else if (id == R.id.navigation_night_mode) {
            switchToPage(HOME);
            pageIndex=3;
            return true;
        }else if (id == R.id.navigation_mores){
            switchToPage(MOREPAGE);
            pageIndex=4;
            return true;
        }
        return false;
    }


    @Override
    protected void onStart() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            SessionHelper.runGetDeviceId(this);
        }

        super.onStart();
            Map <String,String> deviceInfo =new HashMap();
            deviceInfo.put("Lat","");
            deviceInfo.put("Long","");
            deviceInfo.put("NotificationsToken",SessionHelper.getNotificationsToken(this));
            deviceInfo.put("OS","Android "+ Build.MODEL + Build.MANUFACTURER +" Ver : "+ Build.VERSION.RELEASE);
            registerDevice(this,deviceInfo);


    }

    @Override
    protected void onResume() {
        super.onResume();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            SessionHelper.runGetDeviceId(this);
        }

        super.onStart();
        Map <String,String> deviceInfo =new HashMap();
        deviceInfo.put("Lat","");
        deviceInfo.put("Long","");
        deviceInfo.put("NotificationsToken",SessionHelper.getNotificationsToken(this));
        deviceInfo.put("OS","Android "+ Build.MODEL + Build.MANUFACTURER +" Ver : "+ Build.VERSION.RELEASE);
        registerDevice(this,deviceInfo);
        if(pageIndex!=2) {
            navigation.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public void onBackPressed() {
        if (pageIndex == 3 || pageIndex == 2|| pageIndex==1 || pageIndex==4) {
            navigation.setSelectedItemId(R.id.navigation_home);
        } else {
            new MaterialDialog.Builder(this)
                    .title(R.string.app_name)
                    .content(R.string.exit)
                    .positiveText(R.string.yes)
                    .negativeText(R.string.cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .show();
        }
    }



    //Register device on real time data base
    public static void registerDevice(Context context,Object data){
        if(SessionHelper.getDeviceId(context)!=null) {
            FireBaseDataBaseHelper.registerCurrentDevice(SessionHelper.getDeviceId(context), data);
        }
    }
    // add cam on real time DB
    public static void addCam(Object data,Context context){
    FireBaseDataBaseHelper.addCam(data,context);
    }
    //Update device on real time data base
    public static void updateDevice(Context context, Map data){
        if(SessionHelper.getDeviceId(context)!=null) {
            FireBaseDataBaseHelper.updateDeviceInfo(SessionHelper.getDeviceId(context), data);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                   SessionHelper.runGetDeviceId(this);
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
