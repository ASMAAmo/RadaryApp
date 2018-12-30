package com.slashapps.radary.FirebaseHelpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slashapps.radary.R;

import java.util.Map;

/**
 * Created by Eng Ali on 12/27/2018.
 */

public class FireBaseDataBaseHelper {

    public static final String DEVICES = "devices";
    public static final String Cams = "Cams";


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

    public static void  addCam(Object data, final Context context) {
        //This two lines do this
        DatabaseReference cams_push = FireBaseDataBaseHelper.getRootRef().child("Cams").push();
        //This to get this code and store it in var
        String pushKey=cams_push.getKey();
        FirebaseDatabase.getInstance().getReference().child(Cams).child(pushKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.e("Database err" , task.getException().getMessage());
                   SuperActivityToast.create(context
                            , new Style(), Style.TYPE_BUTTON).setButtonText(context.getString(R.string.ok))
                            .setText(task.getException().getMessage())
                            .setDuration(Style.DURATION_LONG)
                            .setFrame(Style.FRAME_LOLLIPOP).setGravity(Gravity.BOTTOM, 0, 0)
                            .setColor(context.getResources().getColor(R.color.colorPrimary))
                            .setAnimations(Style.ANIMATIONS_POP).show();
                }else {
                   SuperActivityToast.create(context
                            , new Style(), Style.TYPE_BUTTON).setButtonText(context.getString(R.string.ok))
                            .setText(context.getResources().getString(R.string.cameraAdded))
                            .setDuration(Style.DURATION_LONG)
                            .setFrame(Style.FRAME_LOLLIPOP).setGravity(Gravity.BOTTOM, 0, 0)
                            .setColor(context.getResources().getColor(R.color.colorPrimary))
                            .setAnimations(Style.ANIMATIONS_POP).show();
                }
            }
        });
    }

    //Get All Cams
    public static DatabaseReference getAllCams (){
        return   FirebaseDatabase.getInstance().getReference().child(Cams);
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

    //get Root ref
    public static DatabaseReference getRootRef (){
        return   FirebaseDatabase.getInstance().getReference();
    }



}
