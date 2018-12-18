package com.slashapps.radary.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.slashapps.radary.Components.BottomNavigation;
import com.slashapps.radary.Fragments.AddCamFragment;
import com.slashapps.radary.Fragments.HomeFragment;
import com.slashapps.radary.Fragments.MoreFragment;
import com.slashapps.radary.Fragments.AddedLocationsFragment;
import com.slashapps.radary.Fragments.NightModeFragment;
import com.slashapps.radary.R;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigation navigation;
    private static final int HOME = 0;
    private static final int MYLOCATION = 1;
    private static final int ADDCAMERA = 2;
    private static final int NIGHTMODE = 3;
    private static final int MOREPAGE=4;
    private int pageIndex = 0;
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
                myToolbar.setVisibility(View.VISIBLE);
                title.setText(this.getResources().getString(R.string.nightmode));
                transaction.replace(R.id.content, new NightModeFragment());
                transaction.commit();
                pageIndex = 3;
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
                navigation.setSelectedItemId(R.id.navigation_night_mode);
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
            return true;
        } else if (id == R.id.navigation_my_location) {
            switchToPage(MYLOCATION);
            return true;
        } else if (id == R.id.navigation_add_camera) {
            switchToPage(ADDCAMERA);
            return true;
        } else if (id == R.id.navigation_night_mode) {
            switchToPage(NIGHTMODE);
            return true;
        }else if (id == R.id.navigation_mores){
            switchToPage(MOREPAGE);
            return true;
        }
        return false;
    }
}
