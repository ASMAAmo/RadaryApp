package com.slashapps.radary.ViewsInterfaces;

import com.slashapps.radary.WebService.Models.Data_login;

import retrofit2.http.Body;

/**
 * Created by user on 25/12/2018.
 */

public interface LoginView {
    void login(Data_login data_login);
    void forgetPass(boolean status);
    void resetPass(boolean status);


    void onError(String err);



}
