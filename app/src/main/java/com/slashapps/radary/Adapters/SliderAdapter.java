package com.slashapps.radary.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slashapps.radary.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eng Ali on 12/16/2018.
 */

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> imges;
    private List<String> texts;

    public SliderAdapter(Context context, List<Integer> images, List<String> texts) {
        this.context = context;
        this.imges = images;
        this.texts = texts;
    }

    @Override
    public int getCount() {
        return imges.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        ImageView imageView =view.findViewById(R.id.slider_img);
        TextView textView = view.findViewById(R.id.slider_tv);


        textView.setText(texts.get(position));
        Picasso.get().load(imges.get(position))
                .into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}