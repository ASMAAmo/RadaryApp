package com.slashapps.radary.Activities;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.slashapps.radary.Adapters.SliderAdapter;
import com.slashapps.radary.ConstantClasss.Prefs;
import com.slashapps.radary.R;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout indicator;
    LinearLayout skipBtn;
    List<Integer> images;
    List<String> textes;
    String checked;
    Prefs myprefs;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myprefs= new Prefs();
        checked="";
        checked= myprefs.getDefaults("checked",SplashActivity.this);
       /* AdRequest request = new AdRequest.Builder()
                .addTestDevice("33BE2250B43518CCDA7DE426D04EE231")  // An example device ID
                .build();
       // request.isTestDevice(this)
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544/1033173712");
        //
        MobileAds.initialize(this,
               getResources().getString( R.string.interstitial_ad_unit_id));

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId( getResources().getString( R.string.interstitial_ad_unit_id)
                );
        mInterstitialAd.loadAd(new AdRequest.Builder().build());*/
        viewPager=findViewById(R.id.viewPager);
        indicator=findViewById(R.id.indicator);
        skipBtn=findViewById(R.id.skip_btn);

        images = new ArrayList<>();
        images.add(R.drawable.slider_man);
        images.add(R.drawable.icslider);
        images.add(R.drawable.map);


        textes = new ArrayList<>();
        textes.add(getResources().getString(R.string.slide1));
        textes.add(getResources().getString(R.string.slide2));
        textes.add(getResources().getString(R.string.addcam));


        viewPager.setAdapter(new SliderAdapter(this, images, textes));
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked.equals("true")){
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    SplashActivity.this.finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,PolicyActivity.class));
                    SplashActivity.this.finish();
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            SplashActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < images.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
