package com.slashapps.radary.ConstantClasss;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;


/**
 * Created by afaf.elshafey on 6/15/2016.
 */
public class Custom_Button extends android.support.v7.widget.AppCompatButton {

    public Custom_Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/droidkufi_regular.ttf"));
    }
}
