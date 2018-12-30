package com.slashapps.radary.Presenters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.LoginView;
import com.slashapps.radary.WebService.API.Constanturl;
import com.slashapps.radary.WebService.API.Router;
import com.slashapps.radary.WebService.Models.ForgetModel;
import com.slashapps.radary.WebService.Models.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 08/11/2018.
 */

public class LoginPresenter {
    Context context;
    LoginView view;
    ACProgressFlower dialog;

    public LoginPresenter(Context context, LoginView view) {
        this.view = view;
        this.context = context;

    }
    public void Login(String mail,String password) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        HashMap input = new HashMap();
        input.put("email",mail);
        input.put("password",password);
        Constanturl.createService(Router.class).login(input).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus()){
                        view.login(response.body().getData());
                    }else {
                        view.onError(response.body().getException());
                    }

                } else {
                    view.onError(context.getResources().getString(R.string.notlogin));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                view.onError(t.getMessage());
            }
        });
    }
    public void forgetPassword(String mail,String password) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        HashMap input = new HashMap();
        input.put("email",mail);

        Constanturl.createService(Router.class).forgetPass(input).enqueue(new Callback<ForgetModel>() {
            @Override
            public void onResponse(Call<ForgetModel> call, Response<ForgetModel> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    ForgetModel model = response.body();
                    view.forgetPass(model.getStatus());
                    // view.affFav(model.getStatus());
                    ;
                    // contactview.getContacts(model.getAbout());
                    // view.getProductdetails(model.getService());
                } else {


                }

            }

            @Override
            public void onFailure(Call<ForgetModel> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }
    public void resetPassword(String activation_key,String password,String con_password) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        HashMap input = new HashMap();
        input.put("activation_key",activation_key);
        input.put("password",password);
        input.put("con_password",con_password);

        Constanturl.createService(Router.class).resetPass(input).enqueue(new Callback<ForgetModel>() {
            @Override
            public void onResponse(Call<ForgetModel> call, Response<ForgetModel> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    ForgetModel model = response.body();
                    if (model.getStatus()==true){
                        SuperActivityToast.create(context
                                , new Style(), Style.TYPE_BUTTON).setButtonText("OK")
                                .setText(model.getException())
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP).setGravity(Gravity.BOTTOM, 0, 0)
                                .setColor(context.getResources().getColor(R.color.colorPrimary))
                                .setAnimations(Style.ANIMATIONS_POP).show();

                    }else {
                        SuperActivityToast.create(context
                                , new Style(), Style.TYPE_BUTTON).setButtonText("OK")
                                .setText(context.getResources().getString(R.string.datasent))
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP).setGravity(Gravity.BOTTOM, 0, 0)
                                .setColor(context.getResources().getColor(R.color.colorPrimary))
                                .setAnimations(Style.ANIMATIONS_POP).show();
                    }
                    view.resetPass(model.getStatus());
                    // view.affFav(model.getStatus());
                    ;
                    // contactview.getContacts(model.getAbout());
                    // view.getProductdetails(model.getService());
                } else {


                }

            }

            @Override
            public void onFailure(Call<ForgetModel> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }
}
