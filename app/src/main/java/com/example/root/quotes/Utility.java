package com.example.root.quotes;

import android.content.Context;
import android.os.Build;

public class Utility {

    protected static int getColorAccordingToAPILevel(Context context, int color)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // api23 and above
            return (context.getResources().getColor(color, null));

        return (context.getResources().getColor(color));
    }
}
