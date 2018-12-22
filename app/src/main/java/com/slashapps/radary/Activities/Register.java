package com.slashapps.radary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.slashapps.radary.R;

public class Register extends BaseActivity implements View.OnClickListener{

    com.andexert.library.RippleView ripple_createAccount,ripple_haveAccount;
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewid();

    }

    private void findViewid() {
        ripple_createAccount=(com.andexert.library.RippleView)findViewById(R.id.rel_signup);
        ripple_haveAccount=(com.andexert.library.RippleView)findViewById(R.id.rel_have_account);
        mToolBar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.create_account));
        ripple_createAccount.setOnClickListener(this);
        ripple_haveAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_have_account :
                Register.this.finish();
                break;
        }
    }
}
