package com.slashapps.radary.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.slashapps.radary.ConstantClasss.LocaleUtils;
import com.slashapps.radary.R;

import static com.slashapps.radary.Activities.BaseActivity.restartApp;
import android.support.v7.widget.Toolbar;

import java.util.Locale;

public class Settingactivity extends AppCompatActivity implements View.OnClickListener{
RelativeLayout relen,rel_ar;
ImageView en_checked;
ImageView ar_checked;
android.support.v7.widget.Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingactivity);
        rel_ar=(RelativeLayout)findViewById(R.id.rel_ar);
        relen=(RelativeLayout)findViewById(R.id.rel_en);
        en_checked=(ImageView) findViewById(R.id.ic_checken);
        ar_checked=(ImageView)findViewById(R.id.ic_checken2);
        en_checked.setVisibility(View.GONE);
        ar_checked.setVisibility(View.GONE);
        mToolBar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getResources().getString(R.string.chooselang));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        relen.setOnClickListener(this);
        rel_ar.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        String local = Locale.getDefault().getDisplayLanguage();
        if (local.equalsIgnoreCase("English")) {
            en_checked.setVisibility(View.VISIBLE);
            ar_checked.setVisibility(View.GONE);
        } else {
            en_checked.setVisibility(View.GONE);
            ar_checked.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_ar:
                LocaleUtils.initialize(Settingactivity.this, LocaleUtils.ARABIC);
                ar_checked.setVisibility(View.VISIBLE);
                en_checked.setVisibility(View.GONE);
                restartApp(Settingactivity.this);
                break;
            case R.id.rel_en:
                LocaleUtils.initialize(Settingactivity.this, LocaleUtils.ENGLISH);
                en_checked.setVisibility(View.VISIBLE);
                ar_checked.setVisibility(View.GONE);
                restartApp(Settingactivity.this);
                break;
        }
    }
}
