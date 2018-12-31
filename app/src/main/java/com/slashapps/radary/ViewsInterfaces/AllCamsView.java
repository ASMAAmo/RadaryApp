package com.slashapps.radary.ViewsInterfaces;

import com.slashapps.radary.WebService.Models.MyPlaces;

import java.util.List;

/**
 * Created by user on 31/12/2018.
 */

public interface AllCamsView {
    public void getAllCams(List<MyPlaces> data);
    void onError(String err);
}
