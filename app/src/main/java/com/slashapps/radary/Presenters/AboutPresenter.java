package com.slashapps.radary.Presenters;

import android.content.Context;
import android.graphics.Color;

import com.slashapps.radary.ViewsInterfaces.AboutView;
import com.slashapps.radary.ViewsInterfaces.Contactavaiew;
import com.slashapps.radary.WebService.API.Constanturl;
import com.slashapps.radary.WebService.API.Router;
import com.slashapps.radary.WebService.Models.Contactmodel;
import com.slashapps.radary.WebService.Models.aboutmodel;

import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 08/11/2018.
 */

public class AboutPresenter {
    Context context;
    AboutView view;
    ACProgressFlower dialog;

    public AboutPresenter(Context context, AboutView view) {
        this.view = view;
        this.context = context;

    }
    public void about() {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        HashMap input = new HashMap();
        Constanturl.createService(Router.class).about().enqueue(new Callback<aboutmodel>() {
            @Override
            public void onResponse(Call<aboutmodel> call, Response<aboutmodel> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    aboutmodel model = response.body();
                    view.getAbout(model.getData());
                   // view.affFav(model.getStatus());
                    ;
                    // contactview.getContacts(model.getAbout());
                    // view.getProductdetails(model.getService());
                } else {


                }

            }

            @Override
            public void onFailure(Call<aboutmodel> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }
}
