package com.slashapps.radary.Presenters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Toast;

import com.slashapps.radary.R;
import com.slashapps.radary.ViewsInterfaces.RegisterView;
import com.slashapps.radary.WebService.API.Constanturl;
import com.slashapps.radary.WebService.API.Generator;
import com.slashapps.radary.WebService.API.Router;
import com.slashapps.radary.WebService.Models.Registermodel;

import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 08/11/2018.
 */

public class RegisterPresenter {
    Context context;
    RegisterView view;
    ACProgressFlower dialog;

    public RegisterPresenter(Context context, RegisterView view) {
        this.view = view;
        this.context = context;

    }
    public void register(String name,String mail,String password) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
//
        HashMap input = new HashMap();
        input.put("name",name);
        input.put("email",mail);
        input.put("password",password);
        // Log.e("inpp", input + "");
        Constanturl.createService(Router.class).Register(input).enqueue(new Callback<Registermodel>() {
            @Override
            public void onResponse(Call<Registermodel> call, Response<Registermodel> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (response.isSuccessful()) {
                    Registermodel model = response.body();
                    if (model.getStatus()==true){
                        Toast.makeText(context,context.getResources().getString(R.string.registerdsuccess),Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context,context.getResources().getString(R.string.registerdsuccess),Toast.LENGTH_LONG).show();

                    }
                    view.register(model.getData());
                   // view.affFav(model.getStatus());
                    ;
                    // contactview.getContacts(model.getAbout());
                    // view.getProductdetails(model.getService());


                } else {

                }

            }

            @Override
            public void onFailure(Call<Registermodel> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }
}
