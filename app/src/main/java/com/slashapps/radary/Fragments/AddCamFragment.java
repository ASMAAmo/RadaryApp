package com.slashapps.radary.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.google.firebase.database.DatabaseReference;
import com.slashapps.radary.Activities.CamLocationActivity;
import com.slashapps.radary.Activities.HomeActivity;
import com.slashapps.radary.Activities.Loginactivity;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddCamFragment extends Fragment implements View.OnClickListener {
    View v;
    EditText latInput,longInput;
    Spinner cam_type_sp;
    Button btn_pickplace;
    RippleView rel_add_camera;
    LinearLayout lin;
    int type_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_add_cam, container, false);
        findViewById();
        if (!latInput.getText().toString().equals("")){
            lin.setVisibility(View.VISIBLE);
        }

        List<String> categories = new ArrayList<String>();
        categories.add(getString(R.string.camtype));
        categories.add(getString(R.string.trafficcam));
        categories.add(getString(R.string.securityambush));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_school_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.sp_school_item);
        // attaching data adapter to spinner
        cam_type_sp.setAdapter(dataAdapter);
        cam_type_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0 : {
                        type_id=0;
                        break;
                    }
                    case 1 : {
                        type_id=1;
                        break;
                    }
                    case 2 : {
                        type_id=2;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void findViewById() {
        latInput=(EditText)v.findViewById(R.id.lat_input);
        btn_pickplace=(Button)v.findViewById(R.id.btn_pickplace);
        rel_add_camera=(RippleView)v.findViewById(R.id.rel_add_camera);
        rel_add_camera.setOnClickListener(this);
        btn_pickplace.setOnClickListener(this);
        lin=(LinearLayout)v.findViewById(R.id.lin);
        longInput=(EditText)v.findViewById(R.id.long_input);
        cam_type_sp=(Spinner)v.findViewById(R.id.cam_type_sp);
        latInput.setOnClickListener(this);
        longInput.setOnClickListener(this);
        latInput.setText(HomeActivity.camLat);
        longInput.setText(HomeActivity.camLng);
    }



    @Override
    public void onResume() {
        super.onResume();
        latInput.setText(HomeActivity.camLat);
        longInput.setText(HomeActivity.camLng);
        if (!latInput.getText().toString().equals("")){
            lin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int view=v.getId();
        Intent intent = new Intent(getActivity(), CamLocationActivity.class);
        switch (view){
            case R.id.btn_pickplace :
                startActivityForResult(intent, 00001);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.rel_add_camera:

          if (latInput.getText().toString().equals("")||longInput.getText().toString().equals("")||type_id==0) {
              Toast.makeText(getActivity(), R.string.choselocation, Toast.LENGTH_LONG).show();
          }else {
                    Map<Object, Object> cam_info = new HashMap();
                    cam_info.put("Lat", HomeActivity.camLat + "");
                    cam_info.put("Long", HomeActivity.camLng + "");
                    cam_info.put("user_token", SessionHelper.getUserSession(getContext()).getUserId());
                    cam_info.put("camType_id",type_id);
                    HomeActivity.addCam(cam_info,getContext());
                    HomeActivity.camLng="";
                    HomeActivity.camLat="";
                    latInput.setText("");
                    longInput.setText("");
                    cam_type_sp.setSelection(0);
                    lin.setVisibility(View.GONE);
                }
                break;

           /* case R.id.long_input :
                startActivityForResult(intent, 00001);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;*/

        }
    }


}
