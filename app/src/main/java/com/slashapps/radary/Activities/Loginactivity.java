package com.slashapps.radary.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.slashapps.radary.Presenters.LoginPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.LoginView;
import com.slashapps.radary.WebService.Models.Data;

public class Loginactivity extends BaseActivity implements View.OnClickListener, LoginView {
    com.andexert.library.RippleView ripple_register, ripple_login;
    Toolbar mToolBar;
    EditText input_email, input_password;
    LoginPresenter presenter;
    Dialog dialog, resetdialog;
    TextView txt_forgetpass;
    String flag;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        findViewid();
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        flag = "";
        presenter = new LoginPresenter(this, Loginactivity.this);

    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void findViewid() {
        ripple_login = (com.andexert.library.RippleView) findViewById(R.id.rel_login);
        ripple_register = (com.andexert.library.RippleView) findViewById(R.id.rel_creatacc);
        txt_forgetpass = (TextView) findViewById(R.id.txt_forgetpass);
        txt_forgetpass.setOnClickListener(this);
        input_password = (EditText) findViewById(R.id.input_password);
        input_email = (EditText) findViewById(R.id.input_email);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.login));
        ripple_login.setOnClickListener(this);
        ripple_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_creatacc:
                startActivity(new Intent(Loginactivity.this, Register.class));
                break;
            case R.id.rel_login:
                if (input_email.getText().toString().equals("")) {
                    input_email.setError(getResources().getString(R.string.mailrequired));
                } else if (input_password.getText().toString().equals("")) {
                    input_password.setError(getResources().getString(R.string.passrequired));
                } else {
                    presenter.Login(input_email.getText().toString(), input_password.getText().toString());
                }
                break;
            case R.id.txt_forgetpass:
                showComumicationDialoge(this);
                break;
        }
    }

    @Override
    public void login(Data data_) {
        SessionHelper.setUserSession(this, data_);
        finish();
    }

    @Override
    public void forgetPass(boolean status) {
        if (status == true) {
            Toast.makeText(this, getResources().getString(R.string.verifcationcodesent), Toast.LENGTH_LONG).show();
            dialog.dismiss();
            showComumicationDialogereset(Loginactivity.this);
        } else {

        }

    }

    public void showComumicationDialoge(Activity activity) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.layout_forget);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText edmail = (EditText) dialog.findViewById(R.id.ed_email);
        com.andexert.library.RippleView send = (com.andexert.library.RippleView) dialog.findViewById(R.id.rel_send);
        // t1.setText(getResources().getString(R.string.bookadded));
        dialog.show();
        //
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edmail.getText().toString().equals("")) {
                    edmail.setError(getResources().getString(R.string.mailrequired));
                } else {
                    presenter.forgetPassword(edmail.getText().toString(), "");
                }

            }
        });
    }

    @Override
    public void resetPass(boolean status) {
        if (status == true) {
            SuperActivityToast.create(this
                    , new Style(), Style.TYPE_BUTTON).setButtonText("oK")
                    .setText(getResources().getString(R.string.passchanged))
                    .setDuration(Style.DURATION_LONG)
                    .setFrame(Style.FRAME_LOLLIPOP).setGravity(Gravity.BOTTOM, 0, 0)
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setAnimations(Style.ANIMATIONS_POP).show();
            resetdialog.dismiss();

        } else {

        }

    }

    @Override
    public void onError(String err) {
        Snackbar.make(findViewById(android.R.id.content), err, Snackbar.LENGTH_LONG).show();
    }

    public void showComumicationDialogereset(Activity activity) {
        resetdialog = new Dialog(activity);
        resetdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        resetdialog.setCanceledOnTouchOutside(true);
        resetdialog.setContentView(R.layout.layout_reset);
        resetdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText edpass = (EditText) resetdialog.findViewById(R.id.ed_pass);
        final EditText ed_newpass = (EditText) resetdialog.findViewById(R.id.ed_newpass);
        com.andexert.library.RippleView send = (com.andexert.library.RippleView) resetdialog.findViewById(R.id.rel_send);
        resetdialog.show();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edpass.getText().toString().equals("")) {
                    edpass.setError(getResources().getString(R.string.mailrequired));
                } else if (ed_newpass.getText().toString().equals("")) {
                    // presenter.forgetPassword(edpass.getText().toString(), "");
                } else {
                    presenter.resetPassword(edpass.getText().toString(), ed_newpass.getText().toString(), edpass.getText().toString());
                }

            }
        });
    }
}
