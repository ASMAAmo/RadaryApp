package com.slashapps.radary.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.slashapps.radary.Presenters.LoginPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.LoginView;
import com.slashapps.radary.WebService.Models.Data_login;

public class Loginactivity extends BaseActivity implements View.OnClickListener, LoginView {
    com.andexert.library.RippleView ripple_register, ripple_login;
    Toolbar mToolBar;
    EditText input_email, input_password;
    LoginPresenter presenter;
    Dialog dialog, resetdialog;
    TextView txt_forgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        findViewid();
        presenter = new LoginPresenter(this, Loginactivity.this);

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
    public void login(Data_login data_login) {
        int userid = data_login.getUserId();
        String token = data_login.getUserToken();
        SessionHelper.setUserSession(this, data_login);
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
        // aboutPresenter.about();
        //
        //
    }

    @Override
    public void resetPass(boolean status) {
        if (status == true) {
            Toast.makeText(this, getResources().getString(R.string.passchanged), Toast.LENGTH_LONG).show();
            resetdialog.dismiss();

        } else {

        }

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
        // t1.setText(getResources().getString(R.string.bookadded));
        resetdialog.show();
        //
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
