package com.slashapps.radary.UserSession;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slashapps.radary.WebService.Models.Data;

import java.lang.reflect.Type;

/**
 * Created by Eng Ali on 12/27/2018.
 */

public class SessionHelper {


    static final String USER_SESSION="user_session";
    static final String NOTIFICATIONS_TOKEN="notifications_token";
    static final String DEVICE_ID="device_id";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    //Store User info or his session
    public static void setUserSession(Context context,Data data_){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(data_);
        editor.putString(USER_SESSION, json);
        editor.commit();
    }

    //Get Current user session
    public static Data getUserSession(Context context){
        String json= getSharedPreferences(context).getString(USER_SESSION, "");
        Data data_ =new Data();
        if(!json.isEmpty()) {
            Type type = new TypeToken<Data>() {}.getType();
            Gson gson = new Gson();
            data_ = gson.fromJson(json, type);
        }
        return data_;
    }


    //Check if user is Login or not
    public static Boolean isLogin(Context context){
       if(getUserSession(context).getUserToken()==null)
           return false;
       else
           return true;
    }



    //Clear User session on logout
    public static void clearSession(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(USER_SESSION);
        editor.commit();
    }



    //Get Device Id
    public static void runGetDeviceId(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("Device Id","No permission");
        }
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        setDeviceId(context,"" + tm.getDeviceId());
    }


    //Set Device Id
    public static void setDeviceId(Context context,String deviceId){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(DEVICE_ID, deviceId);
        editor.commit();
    }

    //Get Device Id from Prefs
    public static String getDeviceId(Context context) {
       return getSharedPreferences(context).getString(DEVICE_ID, null);
    }

    //Store Notifications Token
    public static void setNotificationsToken(Context context,String token){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(NOTIFICATIONS_TOKEN, token);
        editor.commit();
    }

    //Get Notifications Token Id
    public static String getNotificationsToken(Context context){
        return getSharedPreferences(context).getString(NOTIFICATIONS_TOKEN, "");
    }





}
