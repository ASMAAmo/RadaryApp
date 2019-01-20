package com.slashapps.radary.ViewsInterfaces;

import android.location.Location;

/**
 * Created by user on 20/01/2019.
 */

public interface GPSCallback {
    public abstract void onGPSUpdate(Location location);
}
