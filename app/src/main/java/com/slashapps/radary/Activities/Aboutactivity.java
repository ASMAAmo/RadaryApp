package com.slashapps.radary.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.slashapps.radary.Presenters.AboutPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.AboutView;

public class Aboutactivity extends BaseActivity implements AboutView {
    Toolbar mToolBar;
AboutPresenter presenter;
TextView txt_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutactivity);
        mToolBar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        txt_about = (TextView)findViewById(R.id.txt_about);
        presenter = new AboutPresenter(this,Aboutactivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.about));
        presenter.about();
    }

    @Override
    public void getAbout(String data) {
        txt_about.setText(data);
    }
}
