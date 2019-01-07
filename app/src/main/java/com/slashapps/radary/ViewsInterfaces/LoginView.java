package com.slashapps.radary.ViewsInterfaces;

import com.slashapps.radary.WebService.Models.Data;

/**
 * Created by user on 25/12/2018.
 */

public interface LoginView {
    void login(Data data_);
    void forgetPass(boolean status);
    void resetPass(boolean status);


    void onError(String err);



}
