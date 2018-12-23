package com.slashapps.radary.Fragments;


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

import com.slashapps.radary.Adapters.PlacesAdapter;
import com.slashapps.radary.R;

import static java.security.AccessController.getContext;

public class AddedLocationsFragment extends Fragment {


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

    }

    void findViewById(View view){

        placesList=view.findViewById(R.id.added_places_list);
        placesList.setLayoutManager(new LinearLayoutManager(getContext()));
        placesList.setHasFixedSize(true);
        placesList.setAdapter(new PlacesAdapter());
        emptyFlag=view.findViewById(R.id.empty_flag_tv);
        emptyFlag.setVisibility(View.GONE);
    }
}
