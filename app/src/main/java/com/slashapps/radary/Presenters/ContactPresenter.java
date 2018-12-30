package com.slashapps.radary.Presenters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.Contactavaiew;
import com.slashapps.radary.ViewsInterfaces.LoginView;
import com.slashapps.radary.WebService.API.Constanturl;
import com.slashapps.radary.WebService.API.Router;
import com.slashapps.radary.WebService.Models.Contactmodel;
import com.slashapps.radary.WebService.Models.LoginModel;

import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 08/11/2018.
 */

public class ContactPresenter {
    Context context;
    Contactavaiew view;
    ACProgressFlower dialog;

    public ContactPresenter(Context context, Contactavaiew view) {
        this.view = view;
        this.context = context;

    }
    public void contactUs(String mail,String message) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        HashMap input = new HashMap();
        input.put("email",mail);
        input.put("message",message);
        Constanturl.createService(Router.class).contactUs(input).enqueue(new Callback<Contactmodel>() {
            @Override
            public void onResponse(Call<Contactmodel> call, Response<Contactmodel> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    Contactmodel model = response.body();
                    if (model.getStatus()==true){
                        SuperActivityToast.create(context
                                , new Style(), Style.TYPE_BUTTON).setButtonText("Ok")
                                .setText(context.getResources().getString(R.string.datasent))
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP).setGravity(Gravity.BOTTOM, 0, 0)
                                .setColor(context.getResources().getColor(R.color.colorPrimary))
                                .setAnimations(Style.ANIMATIONS_POP).show();
                    }else {
                        SuperActivityToast.create(context
                                , new Style(), Style.TYPE_BUTTON)
                                .setText(model.getException())
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP).setGravity(Gravity.BOTTOM, 0, 0)
                                .setColor(context.getResources().getColor(R.color.colorPrimary))
                                .setAnimations(Style.ANIMATIONS_POP).show();
                    }
                    view.contactUs(model.getStatus());
                   // view.affFav(model.getStatus());
                    ;
                    // contactview.getContacts(model.getAbout());
                    // view.getProductdetails(model.getService());
                } else {


                }

            }

            @Override
            public void onFailure(Call<Contactmodel> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }
}
