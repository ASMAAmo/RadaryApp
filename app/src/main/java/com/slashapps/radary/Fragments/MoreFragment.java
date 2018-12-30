package com.slashapps.radary.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slashapps.radary.Activities.Aboutactivity;
import com.slashapps.radary.Activities.ContactActivity;
import com.slashapps.radary.Activities.HomeActivity;
import com.slashapps.radary.Activities.Loginactivity;
import com.slashapps.radary.Activities.Settingactivity;
import com.slashapps.radary.ConstantClasss.LocaleUtils;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;

import java.util.Locale;

import static com.slashapps.radary.Activities.BaseActivity.restartApp;



public class MoreFragment extends Fragment implements View.OnClickListener{
    View v;
    com.andexert.library.RippleView rel_lang,rel_contact,ripple_about,ripple_login;
    TextView loginBtnLbl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.camLng="";
        HomeActivity.camLat="";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_more, container,
                false);
        findViewsid();
        // Inflate the layout for this fragment
        return v;

    }

    private void findViewsid() {
        ripple_about=(com.andexert.library.RippleView)v.findViewById(R.id.rel_about);
        rel_contact=(com.andexert.library.RippleView)v.findViewById(R.id.rel_contact);
        rel_lang=(com.andexert.library.RippleView)v.findViewById(R.id.rel_lang);
        ripple_login=(com.andexert.library.RippleView)v.findViewById(R.id.rel_login);
        loginBtnLbl=(TextView) v.findViewById(R.id.txt_login);
        rel_contact.setOnClickListener(this);
        rel_lang.setOnClickListener(this);
        ripple_login.setOnClickListener(this);
        ripple_about.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(SessionHelper.isLogin(getContext())){
            loginBtnLbl.setText(getResources().getString(R.string.logout));
        }else {
            loginBtnLbl.setText(getResources().getString(R.string.login));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SessionHelper.isLogin(getContext())){
            loginBtnLbl.setText(getResources().getString(R.string.logout));
        }else {
            loginBtnLbl.setText(getResources().getString(R.string.login));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_about:
                startActivity(new Intent(getActivity(), Aboutactivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.rel_contact:
                startActivity(new Intent(getActivity(), ContactActivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.rel_login:
                if(SessionHelper.isLogin(getContext())){
                    SessionHelper.clearSession(getContext());
                    startActivity(new Intent(getActivity(), Loginactivity.class));
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else {
                    startActivity(new Intent(getActivity(), Loginactivity.class));
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                break;

            case R.id.rel_lang:
                startActivity(new Intent(getActivity(),Settingactivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }
    }


}
