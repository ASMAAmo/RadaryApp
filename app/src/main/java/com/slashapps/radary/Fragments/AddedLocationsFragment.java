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
import com.slashapps.radary.Presenters.MyplacesPresenter;
import com.slashapps.radary.R;
import com.slashapps.radary.UserSession.SessionHelper;
import com.slashapps.radary.ViewsInterfaces.MyplacesView;
import com.slashapps.radary.WebService.Models.Datum;
import com.slashapps.radary.WebService.Models.MyPlaces;

import java.util.List;

public class AddedLocationsFragment extends Fragment implements MyplacesView {

MyplacesPresenter presenter;
   private RecyclerView placesList;
   private TextView emptyFlag;

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
        presenter.getMyplaces();
        }





    void findViewById(View view){
        placesList=view.findViewById(R.id.added_places_list);
        emptyFlag=view.findViewById(R.id.empty_flag_tv);
        emptyFlag.setVisibility(View.GONE);
    }

    @Override
    public void getMyplaces(List<MyPlaces> data) {
        if (data.size()==0){
            emptyFlag.setVisibility(View.VISIBLE);
        }else {
            emptyFlag.setVisibility(View.GONE);
            placesList.setHasFixedSize(true);
            MyplacesAdapter adapter = new MyplacesAdapter(getActivity(),data);
            placesList.setLayoutManager(new LinearLayoutManager(getContext()));
            placesList.setAdapter(adapter);
        }
    }

    @Override
    public void onError(String err) {
        Toast.makeText(getContext(),err,Toast.LENGTH_LONG).show();
    }
}
