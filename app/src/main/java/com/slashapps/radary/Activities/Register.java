package com.slashapps.radary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.slashapps.radary.Presenters.RegisterPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.RegisterView;
import com.slashapps.radary.WebService.Models.Data_register;

public class Register extends BaseActivity implements View.OnClickListener,RegisterView{
    RegisterPresenter presenter;
    com.andexert.library.RippleView ripple_createAccount,ripple_haveAccount;
    Toolbar mToolBar;
EditText input_name,input_email,input_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewid();
        presenter=new RegisterPresenter(this,Register.this);

    }

    private void findViewid() {
        ripple_createAccount=(com.andexert.library.RippleView)findViewById(R.id.rel_signup);
        input_email=(EditText)findViewById(R.id.input_email);
        input_name=(EditText)findViewById(R.id.input_name);
        input_password=(EditText)findViewById(R.id.input_password);
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
            case R.id.rel_signup:
                if (input_name.getText().toString().equals("")){
                    input_name.setError("required");
                }else if (input_email.getText().toString().equals("")){
                    input_email.setError("required");
                }else if (input_password.getText().toString().equals("")){
                    input_password.setError("required");
                }else {
                    presenter.register(input_name.getText().toString(),input_email.getText().toString(),input_password.getText().toString());
                }
                break;
        }
    }

    @Override
    public void register(Data_register data) {

    }
}
