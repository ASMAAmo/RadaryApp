package com.slashapps.radary.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slashapps.radary.R;

/**
 * Created by Eng Ali on 12/18/2018.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.Vh> {


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_place,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
       /* if( position %2 == 1 ){
            holder.view.setBackgroundColor(Color.LTGRAY);
        } else{
            holder.view.setBackgroundColor(Color.WHITE);
        }*/
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public static class Vh extends RecyclerView.ViewHolder{
        private TextView placeNameTv,latLngTv;
        private View view;

        public Vh(View itemView) {
            super(itemView);
            view=itemView;
            placeNameTv=itemView.findViewById(R.id.place_name_tv);
            latLngTv=itemView.findViewById(R.id.lat_long_tv);
        }
    }
}
