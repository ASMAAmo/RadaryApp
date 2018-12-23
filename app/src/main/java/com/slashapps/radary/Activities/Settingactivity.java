package com.slashapps.radary.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.slashapps.radary.ConstantClasss.LocaleUtils;
import com.slashapps.radary.R;

import static com.slashapps.radary.Activities.BaseActivity.restartApp;
import android.support.v7.widget.Toolbar;
public class Settingactivity extends AppCompatActivity implements View.OnClickListener{
RelativeLayout relen,rel_ar;
android.support.v7.widget.Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingactivity);
        rel_ar=(RelativeLayout)findViewById(R.id.rel_ar);
        relen=(RelativeLayout)findViewById(R.id.rel_en);
        mToolBar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getResources().getString(R.string.chooselang));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        relen.setOnClickListener(this);
        rel_ar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_ar:
                LocaleUtils.initialize(Settingactivity.this, LocaleUtils.ARABIC);
                restartApp(Settingactivity.this);
                break;
            case R.id.rel_en:
                LocaleUtils.initialize(Settingactivity.this, LocaleUtils.ARABIC);
                restartApp(Settingactivity.this);
                break;
        }
    }
}
