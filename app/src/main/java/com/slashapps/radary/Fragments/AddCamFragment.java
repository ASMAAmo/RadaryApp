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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.slashapps.radary.Activities.CamLocationActivity;
import com.slashapps.radary.Activities.HomeActivity;
import com.slashapps.radary.Activities.Loginactivity;
import com.slashapps.radary.ConstantClasss.Prefs;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.slashapps.radary.Activities.HomeActivity.updateDevice;


public class AddCamFragment extends Fragment implements View.OnClickListener {
    View v;
    EditText latInput,longInput;
    Spinner cam_type_sp;
    Button btn_pickplace;
    RippleView rel_add_camera;
    LinearLayout lin;
Prefs myPrefs;
int type_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_add_cam, container, false);
        findViewById();
        if (!latInput.getText().toString().equals("")){
            //latInput.setVisibility(View.GONE);
            lin.setVisibility(View.VISIBLE);

        }
        myPrefs= new Prefs();
        List<String> categories = new ArrayList<String>();
        categories.add("نوع الكاميرا");
        categories.add("كاميرا مروريه");
        categories.add("نقطه تفتيش");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_school_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.sp_school_item);
        // attaching data adapter to spinner
        cam_type_sp.setAdapter(dataAdapter);
        cam_type_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position==0){
type_id = 0;
               }else if (position==1){
                   type_id = 1;
               }else {
                   type_id = 2;
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
    public void onClick(View v) {
        int view=v.getId();
        Intent intent = new Intent(getActivity(), CamLocationActivity.class);
        switch (view){
            case R.id.btn_pickplace :
                startActivityForResult(intent, 00001);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.rel_add_camera:
                if (myPrefs.getDefaults("token",getActivity()).equals("")){
                    Toast.makeText(getActivity(),getResources().getString(R.string.pleaselogin),Toast.LENGTH_LONG).show();
                    Intent loginintent = new Intent(getActivity(),Loginactivity.class);
                    ///intent.putExtra("flg","1");
                    startActivity(loginintent);
                }else if (latInput.getText().toString().equals("")||longInput.getText().toString().equals("")){
                   Toast.makeText(getActivity(),"choose location",Toast.LENGTH_LONG).show();
                }else {
                    Map<Object, Object> cam_info = new HashMap();
                    cam_info.put("Lat", HomeActivity.camLat + "");
                    cam_info.put("Long", HomeActivity.camLng + "");
                    cam_info.put("token", myPrefs.getDefaults("token",getActivity()));
                    cam_info.put("type",type_id );
                    cam_info.put("camType_id",type_id);
                    FireBaseDataBaseHelper.addCam(cam_info);
                }
                break;

           /* case R.id.long_input :
                startActivityForResult(intent, 00001);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;*/

        }
    }


}
