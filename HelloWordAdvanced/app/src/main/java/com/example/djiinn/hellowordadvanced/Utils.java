package com.example.djiinn.hellowordadvanced;

import android.app.Activity;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.Display;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by djiinn on 01.12.2016.
 */
public class Utils {
    // Return the width of current display
    public static int GetDisplayWidth(Activity activity)
    {
        //capture the size of the devices screen
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.widthPixels;
    }

    // Return the heigth of current display
    public static int GetDisplayHeigth(Activity activity)
    {
        //capture the size of the devices screen
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.heightPixels;
    }
}
