package com.slashapps.radary.Fragments;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.slashapps.radary.Activities.CamLocationActivity;
import com.slashapps.radary.Activities.HomeActivity;
import com.slashapps.radary.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AddCamFragment extends Fragment implements View.OnClickListener {
    View v;
    EditText latInput,longInput;
    Spinner cam_type_sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_add_cam, container, false);
        findViewById();
        List<String> categories = new ArrayList<String>();
        categories.add("نوع الكاميرا");
        categories.add("كاميرا مروريه");
        categories.add("نقطه تفتيش");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_school_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.sp_school_item);
        // attaching data adapter to spinner
        cam_type_sp.setAdapter(dataAdapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void findViewById() {
        latInput=(EditText)v.findViewById(R.id.lat_input);
        longInput=(EditText)v.findViewById(R.id.long_input);
        cam_type_sp=(Spinner)v.findViewById(R.id.cam_type_sp);
        latInput.setOnClickListener(this);
        longInput.setOnClickListener(this);
        latInput.setText(HomeActivity.camLat);
        longInput.setText(HomeActivity.camLng);
    }

    @Override
    public void onClick(View v) {
        int view=v.getId();
        Intent intent = new Intent(getActivity(), CamLocationActivity.class);
        switch (view){
            case R.id.lat_input :
                startActivityForResult(intent, 00001);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.long_input :
                startActivityForResult(intent, 00001);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

        }
    }


}
