package com.s4plabs.logbook.ui;

import android.animation.TimeInterpolator;

/**
 * Created by lenovo pc on 7/8/2016.
 */
public class item {
    public final String description;
    public final int colorId1;
    public final int colorId2;
    public final TimeInterpolator interpolator;

    public item(String description, int colorId1, int colorId2, TimeInterpolator interpolator) {
        this.description = description;
        this.colorId1 = colorId1;
        this.colorId2 = colorId2;
        this.interpolator = interpolator;
    }
}
