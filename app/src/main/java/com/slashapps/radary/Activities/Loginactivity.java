package com.slashapps.radary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.slashapps.radary.Presenters.LoginPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.LoginView;
import com.slashapps.radary.WebService.Models.Data_login;

public class Loginactivity extends BaseActivity implements View.OnClickListener,LoginView {
    com.andexert.library.RippleView ripple_register,ripple_login;
    Toolbar mToolBar;
    EditText input_email,input_password;
    LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        findViewid();
        presenter=new LoginPresenter(this,Loginactivity.this);

    }

    private void findViewid() {
        ripple_login=(com.andexert.library.RippleView)findViewById(R.id.rel_login);
        ripple_register=(com.andexert.library.RippleView)findViewById(R.id.rel_creatacc);
        input_password=(EditText)findViewById(R.id.input_password);
        input_email=(EditText)findViewById(R.id.input_email);
        mToolBar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.login));
        ripple_login.setOnClickListener(this);
        ripple_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_creatacc :
                startActivity(new Intent(Loginactivity.this,Register.class));
                break;
            case R.id.rel_login:
                if (input_email.getText().toString().equals("")){
                   input_email.setError(getResources().getString(R.string.mailrequired));
                }else if (input_password.getText().toString().equals("")){
                    input_password.setError(getResources().getString(R.string.passrequired));
                }else {
                   presenter.Login(input_email.getText().toString(),input_password.getText().toString());
                }
                break;
        }
    }

    @Override
    public void login(Data_login data_login) {
        int userid= data_login.getUserId();
        String token = data_login.getUserToken();


    }
}
