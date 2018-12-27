package com.slashapps.radary.FirebaseHelpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by Eng Ali on 12/27/2018.
 */

public class FireBaseDataBaseHelper {

    public static final String DEVICES = "devices";


    //This to assign new device
    public static void registerCurrentDevice (String deviceId,Object data){
        FirebaseDatabase.getInstance().getReference().child(DEVICES).child(deviceId).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.e("Database err" , task.getException().getMessage());
                }
            }
        });
    }


    //This to assign new device
    public static void updateDeviceInfo (String deviceId, Map data){
        FirebaseDatabase.getInstance().getReference().child(DEVICES).child(deviceId).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.e("Database err" , task.getException().getMessage());
                }
            }
        });
    }


    //Get Device data by id
    public static DatabaseReference getCurrentDeviceById (String deviceId){
        return   FirebaseDatabase.getInstance().getReference().child(DEVICES).child(deviceId);
    }



}
