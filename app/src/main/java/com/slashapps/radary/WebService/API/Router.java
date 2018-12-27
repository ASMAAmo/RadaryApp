package com.slashapps.radary.WebService.API;

import com.slashapps.radary.WebService.Models.LoginModel;
import com.slashapps.radary.WebService.Models.Registermodel;

import retrofit2.Call;
import retrofit2.http.Body;
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
}
