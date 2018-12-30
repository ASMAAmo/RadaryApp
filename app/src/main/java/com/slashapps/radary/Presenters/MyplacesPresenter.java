package com.slashapps.radary.Presenters;

import android.content.Context;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.AboutView;
import com.slashapps.radary.ViewsInterfaces.MyplacesView;
import com.slashapps.radary.WebService.API.Constanturl;
import com.slashapps.radary.WebService.API.Router;
import com.slashapps.radary.WebService.Models.MyPlaces;
import com.slashapps.radary.WebService.Models.aboutmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 08/11/2018.
 */

public class MyplacesPresenter {
    Context context;
    MyplacesView view;
    ACProgressFlower dialog;

    public MyplacesPresenter(Context context, MyplacesView view) {
        this.view = view;
        this.context = context;

    }
    public void getMyplaces() {

        //This is loading dialog
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();


        //Get All cams
        FireBaseDataBaseHelper.getAllCams().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<MyPlaces>myPlaces=new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    //Get user cams by his  id
                    if(dataSnapshot1.child("user_token").getValue().toString().equals(SessionHelper.getUserSession(context).getUserId().toString())){
                        MyPlaces place =new MyPlaces();
                        place.setLat(dataSnapshot1.child("Lat").getValue().toString());
                        place.setLng(dataSnapshot1.child("Long").getValue().toString());
                        place.setCamTypeId(dataSnapshot1.child("camType_id").getValue().toString());
                        place.setUser_token(dataSnapshot1.child("user_token").getValue().toString());
                        myPlaces.add(place);
                    }
                }
                if (dialog.isShowing())
                    dialog.dismiss();
                view.getMyplaces(myPlaces);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (dialog.isShowing())
                    dialog.dismiss();
                view.onError(databaseError.getMessage());

            }
        });

   }
}
