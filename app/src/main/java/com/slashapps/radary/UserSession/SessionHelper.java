package com.slashapps.radary.UserSession;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slashapps.radary.R;
import com.slashapps.radary.WebService.Models.Data_login;

import java.lang.reflect.Type;

import static android.content.ContentValues.TAG;

/**
 * Created by Eng Ali on 12/27/2018.
 */

public class SessionHelper {


    static final String USER_SESSION="user_session";
    static final String NOTIFICATIONS_TOKEN="notifications_token";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    //Store User info or his session
    public static void setUserSession(Context context,Data_login data_login){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(data_login);
        editor.putString(USER_SESSION, json);
        editor.commit();
    }

    //Get Current user session
    public static Data_login getUserSession(Context context){
        String json= getSharedPreferences(context).getString(USER_SESSION, "");
        Data_login data_login=new Data_login();
        if(!json.isEmpty()) {
            Type type = new TypeToken<Data_login>() {}.getType();
            Gson gson = new Gson();
            data_login= gson.fromJson(json, type);
        }
        return data_login;
    }


    //Clear User session on logout
    public static void clearSession(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_SESSION, null);
        editor.commit();
    }




    //Get Device Id
    public static String getDeviceId(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("Device Id","No permission");
        }
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return  "" + tm.getDeviceId();
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
