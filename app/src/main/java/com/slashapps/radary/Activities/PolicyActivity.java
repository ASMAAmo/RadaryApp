package com.slashapps.radary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.slashapps.radary.ConstantClasss.Prefs;
import com.slashapps.radary.Presenters.PolicyPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.PolicyView;

public class PolicyActivity extends AppCompatActivity implements PolicyView{
CheckBox chechagree;
TextView txt_policy;
    PolicyPresenter presenter;
    Prefs myPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        myPrefs=new Prefs();
        chechagree=(CheckBox)findViewById(R.id.chechagree);
        presenter=new PolicyPresenter(this,PolicyActivity.this);
        txt_policy=(TextView)findViewById(R.id.txt_policy);
        presenter.getPolicy();
        //
        chechagree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
            myPrefs.setDefaults("checked","true",PolicyActivity.this);
                    startActivity(new Intent(PolicyActivity.this,HomeActivity.class));
                }

            }
        });
    }

    @Override
    public void getPolicy(String data) {
        txt_policy.setText(data);
    }
}
