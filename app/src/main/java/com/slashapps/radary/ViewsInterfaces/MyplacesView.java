package com.slashapps.radary.ViewsInterfaces;

import com.slashapps.radary.WebService.Models.Datum;
import com.slashapps.radary.WebService.Models.MyPlaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29/12/2018.
 */

public interface MyplacesView {
     void getMyplaces(List<MyPlaces> data);
     void onError(String err);
}
