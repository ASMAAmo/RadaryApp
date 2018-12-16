package com.slashapps.radary.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.slashapps.radary.Adapters.SliderAdapter;
import com.slashapps.radary.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        viewPager=findViewById(R.id.viewPager);
        indicator=findViewById(R.id.indicator);
        skipBtn=findViewById(R.id.skip_btn);

        images = new ArrayList<>();
        images.add(R.drawable.img_slider1);
        images.add(R.drawable.imge_slider2);


        textes = new ArrayList<>();
        textes.add(getResources().getString(R.string.slide1));
        textes.add(getResources().getString(R.string.slide2));


        viewPager.setAdapter(new SliderAdapter(this, images, textes));
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                SplashActivity.this.finish();
            }
        });
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
