package com.slashapps.radary.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.slashapps.radary.Activities.Placesmap;
import com.slashapps.radary.R;
import com.slashapps.radary.WebService.Models.Datum;
import com.slashapps.radary.WebService.Models.MyPlaces;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by ahmed on 11/18/2017.
 */

public class MyplacesAdapter extends RecyclerView.Adapter<MyplacesAdapter.ViewHolder> {

    OnItemClickListener onItemClickListener;
    public static int num;
    Context context;
    List<MyPlaces> places_list;



    public MyplacesAdapter(Context context, List<MyPlaces> places_list) {
        this.context = context;
        this.places_list = places_list;

    }


    public interface OnItemClickListener {
        void onclick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_place, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //animate(holder);
        final MyPlaces faqModel = places_list.get(position);
        switch (faqModel.getActive()+""){
            case "0" :{
                holder.status_tv.setText(context.getResources().getString(R.string.pending));
                break;
            }
        }
        holder.lat_long_tv.setText(new DecimalFormat("##.#####").format(Double.parseDouble(faqModel.getLat()))+" ,"+ new DecimalFormat("##.#####").format(Double.parseDouble(faqModel.getLng())));
        switch (faqModel.getCamTypeId()){
            case "1" :{
                holder.place_name_tv.setText(context.getResources().getString(R.string.trafficcam));
                break;
            }
            case "2" :{
                holder.place_name_tv.setText(context.getResources().getString(R.string.securityambush));
                break;
            }
        }
        //
        holder.relall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Placesmap.class);
                intent.putExtra("lat",faqModel.getLat());
                intent.putExtra("lang",faqModel.getLng());
                intent.putExtra("camId",faqModel.getCamTypeId());
                Log.e("latlang",faqModel.getLat()+" "+faqModel.getLng()+"");
                context.startActivity(intent);
            }
        });



    }


    @Override
    public int getItemCount() {
        return places_list.size();
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView lat_long_tv, place_name_tv , status_tv;
RelativeLayout relall;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            relall=(RelativeLayout)itemView.findViewById(R.id.relall);
            place_name_tv = (TextView) itemView.findViewById(R.id.place_name_tv);
            lat_long_tv = (TextView) itemView.findViewById(R.id.lat_long_tv);
            status_tv=(TextView) itemView.findViewById(R.id.status);


        }

        public void animate(RecyclerView.ViewHolder viewHolder) {
            final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator);
            viewHolder.itemView.setAnimation(animAnticipateOvershoot);
        }


    }
}
