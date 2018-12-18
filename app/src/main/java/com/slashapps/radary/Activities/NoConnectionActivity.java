package com.slashapps.radary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andexert.library.RippleView;
import com.slashapps.radary.R;

public class NoConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    com.andexert.library.RippleView ripple_tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        ripple_tryAgain=findViewById(R.id.rel_try_again);
        ripple_tryAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        switch (view){
            case  R.id.rel_try_again :
                startActivity(new Intent(NoConnectionActivity.this,SplashActivity.class));
                NoConnectionActivity.this.finish();
        }
    }
}
