package com.slashapps.radary.Presenters;

import android.content.Context;
import android.graphics.Color;

import com.slashapps.radary.ViewsInterfaces.AboutView;
import com.slashapps.radary.ViewsInterfaces.MyplacesView;
import com.slashapps.radary.WebService.API.Constanturl;
import com.slashapps.radary.WebService.API.Router;
import com.slashapps.radary.WebService.Models.MyPlaces;
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

public class MyplacesPresenter {
    Context context;
    MyplacesView view;
    ACProgressFlower dialog;

    public MyplacesPresenter(Context context, MyplacesView view) {
        this.view = view;
        this.context = context;

    }
    public void getMyplaces(String token,String offset,String limit) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        HashMap input = new HashMap();
        input.put("user_token",token);
        input.put("offset",offset);
        input.put("limit",limit);
        Constanturl.createService(Router.class).getMyplaces(input).enqueue(new Callback<MyPlaces>() {
            @Override
            public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    MyPlaces model = response.body();
                    view.getMyplaces(model.getData());
                   // view.affFav(model.getStatus());
                    ;
                    // contactview.getContacts(model.getAbout());
                    // view.getProductdetails(model.getService());
                } else {


                }

            }

            @Override
            public void onFailure(Call<MyPlaces> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }
}
