package com.slashapps.radary.Presenters;

import android.content.Context;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.slashapps.radary.FirebaseHelpers.FireBaseDataBaseHelper;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.AllCamsView;
import com.slashapps.radary.ViewsInterfaces.MyplacesView;
import com.slashapps.radary.WebService.Models.MyPlaces;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by user on 08/11/2018.
 */

public class AllCamsPresenter {
    Context context;
    AllCamsView view;
    ACProgressFlower dialog;

    public AllCamsPresenter(Context context, AllCamsView view) {
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
                    //Get All cams
                        MyPlaces place =new MyPlaces();
                        place.setLat(dataSnapshot1.child("Lat").getValue().toString());
                        place.setLng(dataSnapshot1.child("Long").getValue().toString());
                        place.setCamTypeId(dataSnapshot1.child("camType_id").getValue().toString());
                        place.setUser_token(dataSnapshot1.child("user_token").getValue().toString());
                        myPlaces.add(place);
                }
                if (dialog.isShowing())
                    dialog.dismiss();
                view.getAllCams(myPlaces);
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
