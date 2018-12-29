package com.slashapps.radary.Activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.MapView;
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

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigation navigation;
    private static final int HOME = 0;
    private static final int MYLOCATION = 1;
    private static final int ADDCAMERA = 2;
    private static final int NIGHTMODE = 3;
    private static final int MOREPAGE=4;
    public static int pageIndex = 0;
    public static String camLat="";
    public static String camLng="";
    private TextView title;
    public static Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        navigation = findViewById(R.id.navigation);
        title = findViewById(R.id.title);
        navigation.setSelectedItemId(R.id.navigation_home);

        Log.e("Test Notification Token",SessionHelper.getNotificationsToken(HomeActivity.this));
    }



    private void switchToPage(int page) {
        FragmentTransaction transaction = getTransaction();
        switch (page) {
            case HOME:
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.home));
                transaction.replace(R.id.content, new HomeFragment());
                transaction.commit();
                pageIndex = 0;
                break;
            case MYLOCATION:
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.mylocation));
                transaction.replace(R.id.content, new AddedLocationsFragment());
                transaction.commit();
                pageIndex = 1;
                break;
            case ADDCAMERA:
                myToolbar.setVisibility(View.GONE);
                transaction.replace(R.id.content, new AddCamFragment());
                transaction.commit();
                pageIndex = 2;
                break;
            case NIGHTMODE:
                pageIndex = 3;
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.nightmode));
                transaction.replace(R.id.content, new HomeFragment());
                transaction.commit();
                break;

            case MOREPAGE:
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.more));
                transaction.replace(R.id.content, new MoreFragment());
                transaction.commit();
                pageIndex = 4;
                break;
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        /// Back to last fragment
        switch (pageIndex) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_my_location);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_add_camera);
                break;
            case 3:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 4:
                navigation.setSelectedItemId(R.id.navigation_mores);
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
            pageIndex=1;
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
        super.onStart();
        Map <String,String> deviceInfo =new HashMap();
        deviceInfo.put("Lat","");
        deviceInfo.put("Long","");
        deviceInfo.put("NotificationsToken",SessionHelper.getNotificationsToken(this));
        deviceInfo.put("OS","Android");
        registerDevice(this,deviceInfo);
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
        FireBaseDataBaseHelper.registerCurrentDevice(SessionHelper.getDeviceId(context),data);
    }

    //Update device on real time data base
    public static void updateDevice(Context context, Map data){
        FireBaseDataBaseHelper.updateDeviceInfo(SessionHelper.getDeviceId(context),data);
    }

}
