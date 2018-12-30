package com.slashapps.radary.WebService.API;

import com.slashapps.radary.WebService.Models.Contactmodel;
import com.slashapps.radary.WebService.Models.ForgetModel;
import com.slashapps.radary.WebService.Models.LoginModel;
import com.slashapps.radary.WebService.Models.MyPlaces;
import com.slashapps.radary.WebService.Models.Registermodel;
import com.slashapps.radary.WebService.Models.aboutmodel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Eng Ali on 12/16/2018.
 */

public interface Router {
    @POST("api/v1/auth/signup")
    Call<Registermodel> Register(@Body Object data);
    // login
    @POST("api/v1/auth/login")
    Call<LoginModel> login(@Body Object data);
    // contactUs
    @POST("api/v1/utility/contact")
    Call<Contactmodel> contactUs(@Body Object data);
    // about
    @GET("api/v1/utility/about")
    Call<aboutmodel> about();
    // forgetPass
    @POST("api/v1/auth/forget")
    Call<ForgetModel> forgetPass(@Body Object data);
    // reset
    @POST("api/v1/auth/reset")
    Call<ForgetModel> resetPass(@Body Object data);
    // get places
    @POST("api/v1/camera/my")
    Call<MyPlaces> getMyplaces(@Body Object data);
}
