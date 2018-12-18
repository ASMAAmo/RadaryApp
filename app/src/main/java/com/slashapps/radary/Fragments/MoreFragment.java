package com.slashapps.radary.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slashapps.radary.Activities.Aboutactivity;
import com.slashapps.radary.Activities.ContactActivity;
import com.slashapps.radary.Activities.Loginactivity;
import com.slashapps.radary.ConstantClasss.LocaleUtils;
import com.slashapps.radary.R;

import java.util.Locale;

import static com.slashapps.radary.Activities.BaseActivity.restartApp;


public class MoreFragment extends Fragment implements View.OnClickListener{
    View v;
    com.andexert.library.RippleView rel_lang,rel_contact,ripple_about,ripple_login;
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
        rel_contact.setOnClickListener(this);
        rel_lang.setOnClickListener(this);
        ripple_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_about:
                startActivity(new Intent(getActivity(),Aboutactivity.class));
                break;
            case R.id.rel_contact:
                startActivity(new Intent(getActivity(),ContactActivity.class));
                break;
            case R.id.rel_login:
                startActivity(new Intent(getActivity(),Loginactivity.class));
                break;

            case R.id.rel_lang:
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                CharSequence items[] = new CharSequence[] {"عربي","ENGLISH"};

                int selectedLang = 0;
                String local = Locale.getDefault().getDisplayLanguage();
                if (local.equalsIgnoreCase("English")) {
                    selectedLang = 1;
                } else {
                    selectedLang = 0;
                }
                adb.setSingleChoiceItems(items, selectedLang, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface d, int n) {




                        switch (n){
                            case 0 :
                                LocaleUtils.initialize(getContext(),LocaleUtils.ARABIC);
                                restartApp(getContext());
                                break;
                            case 1 :
                                LocaleUtils.initialize(getContext(),LocaleUtils.ENGLISH);
                                restartApp(getContext());
                                break;
                        }
                    }

                });

                adb.setNegativeButton(getResources().getString(R.string.cancel), null);
                adb.setTitle(getResources().getString(R.string.selectlang));
                adb.show();
                break;
        }
    }
}
