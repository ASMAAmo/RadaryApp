package com.slashapps.radary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.slashapps.radary.R;

public class Loginactivity extends AppCompatActivity implements View.OnClickListener {
    com.andexert.library.RippleView ripple_register,ripple_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        findViewid();
    }

    private void findViewid() {
        ripple_login=(com.andexert.library.RippleView)findViewById(R.id.rel_login);
        ripple_register=(com.andexert.library.RippleView)findViewById(R.id.rel_creatacc);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_creatacc:
                startActivity(new Intent(this,Register.class));
                break;
        }
    }
}
