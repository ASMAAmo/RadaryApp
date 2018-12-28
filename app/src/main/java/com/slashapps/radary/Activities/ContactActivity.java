package com.slashapps.radary.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.andexert.library.RippleView;
import com.slashapps.radary.Presenters.ContactPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.Contactavaiew;

public class ContactActivity extends BaseActivity implements Contactavaiew,View.OnClickListener {
    Toolbar mToolBar;
    ContactPresenter presenter;
    EditText input_email,input_message;
    RippleView rel_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mToolBar=findViewById(R.id.toolbar);
        findViewsid();
        setSupportActionBar(mToolBar);
        presenter = new ContactPresenter(this,ContactActivity.this);
        getSupportActionBar().setTitle(getResources().getString(R.string.contact));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void findViewsid() {
        input_message = (EditText)findViewById(R.id.input_message);
        input_email=(EditText)findViewById(R.id.input_email);
        rel_send = (RippleView)findViewById(R.id.rel_send);
        rel_send.setOnClickListener(this);

    }

    @Override
    public void contactUs(boolean status) {
        if (status==true){

        }else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_send:
                if (input_email.getText().toString().equals("")){
                    input_email.setError(getResources().getString(R.string.mailrequired));
                }else if (input_message.getText().toString().equals("")){
                    input_message.setError(getResources().getString(R.string.required));

                }else {
                    presenter.contactUs(input_email.getText().toString(),input_message.getText().toString());
                }
                break;
        }
    }
}
