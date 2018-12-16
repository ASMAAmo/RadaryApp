package com.slashapps.radary.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.slashapps.radary.R;

import java.lang.reflect.Field;

/**
 * Created by Eng Ali on 12/16/2018.
 */

public class BottomNavigation extends LinearLayout {
    private BottomNavigationView navigation;

    public BottomNavigation(Context context) {
        super(context);
        init();
    }

    public BottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomNavigation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.component_bottom_navigation, this);
        navigation = findViewById(R.id.BottomNavigationView);
        navigation.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) getContext());
        BottomNavigationViewHelper.removeShiftMode(navigation);
    }

    public void setSelectedItemId(int selectedItemId) {
        this.navigation.setSelectedItemId(selectedItemId);
    }

    static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }
}
