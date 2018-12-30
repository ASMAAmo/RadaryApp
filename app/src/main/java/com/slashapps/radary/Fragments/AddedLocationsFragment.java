package com.slashapps.radary.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.slashapps.radary.Activities.Loginactivity;
import com.slashapps.radary.Adapters.MyplacesAdapter;
import com.slashapps.radary.Adapters.PlacesAdapter;
import com.slashapps.radary.ConstantClasss.Prefs;
import com.slashapps.radary.Presenters.MyplacesPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.MyplacesView;
import com.slashapps.radary.WebService.Models.Datum;
import com.slashapps.radary.WebService.Models.MyPlaces;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class AddedLocationsFragment extends Fragment implements MyplacesView {

MyplacesPresenter presenter;
   private RecyclerView placesList;
   private TextView emptyFlag;

    List<MyPlaces> places_list;
Prefs myPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        presenter = new MyplacesPresenter(getActivity(),AddedLocationsFragment.this);
        myPrefs = new Prefs();
        if (myPrefs.getDefaults("token",getActivity()).equals("")){
            Toast.makeText(getActivity(),getResources().getString(R.string.pleaselogin),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(),Loginactivity.class);
            intent.putExtra("flg","1");
            startActivity(intent);
        }else {
            presenter.getMyplaces(myPrefs.getDefaults("token",getActivity()),"0","100");

        }

    }

    void findViewById(View view){

        placesList=view.findViewById(R.id.added_places_list);

       // placesList.setAdapter(new MyplacesAdapter();
        emptyFlag=view.findViewById(R.id.empty_flag_tv);
        emptyFlag.setVisibility(View.GONE);
    }

    @Override
    public void getMyplaces(List<Datum> data) {
        if (data.size()==0){

        }else {

            placesList.setHasFixedSize(true);
            MyplacesAdapter adapter = new MyplacesAdapter(getActivity(),data);
            placesList.setLayoutManager(new LinearLayoutManager(getContext()));
            // placesList.setLayoutManager(new LinearLayoutManager(getActivity()));
            placesList.setAdapter(adapter);
        }
    }
}
