package com.slashapps.radary.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.slashapps.radary.R;
import com.slashapps.radary.WebService.Models.Datum;
import com.slashapps.radary.WebService.Models.MyPlaces;

import java.util.List;


/**
 * Created by ahmed on 11/18/2017.
 */

public class MyplacesAdapter extends RecyclerView.Adapter<MyplacesAdapter.ViewHolder> {

    OnItemClickListener onItemClickListener;
    public static int num;
    Context context;
    List<Datum> places_list;
    boolean ischecked = false;
    Button btncall;
    String planiid;

    public static String pdfpath = "https://zajelme.com/";
    public String url = "http://fahmney.com/";
    public String delegateId, userid, serviceid, quat;
    //Add book url to this link and test it


    public MyplacesAdapter(Context context, List<Datum> places_list) {
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
        final Datum faqModel = places_list.get(position);
        holder.lat_long_tv.setText(""+ faqModel.getLatitude()+" ,"+ faqModel.getLongitude());
holder.place_name_tv.setText(faqModel.getType());

    }


    @Override
    public int getItemCount() {
        return places_list.size();
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linall;
        View mView;
        TextView lat_long_tv, place_name_tv;
        ;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            place_name_tv = (TextView) itemView.findViewById(R.id.place_name_tv);
            lat_long_tv = (TextView) itemView.findViewById(R.id.lat_long_tv);
            // linall=(LinearLayout)itemView.findViewById(R.id.linall);
        }

        public void animate(RecyclerView.ViewHolder viewHolder) {
            final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator);
            viewHolder.itemView.setAnimation(animAnticipateOvershoot);
        }


    }
}
