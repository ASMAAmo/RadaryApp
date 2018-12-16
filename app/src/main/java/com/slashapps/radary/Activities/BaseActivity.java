package com.slashapps.radary.Activities;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.slashapps.radary.R;

public class BaseActivity extends AppCompatActivity {

    //r
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check Os Ver For Set Status Bar
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }


    @SuppressLint("PrivateResource")
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @SuppressLint("PrivateResource")
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @SuppressLint("PrivateResource")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }


    @SuppressLint("ResourceType")
    public android.support.v4.app.FragmentTransaction getTransaction() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.abc_fade_in,
                R.anim.abc_fade_out);
        return transaction;
    }
}
