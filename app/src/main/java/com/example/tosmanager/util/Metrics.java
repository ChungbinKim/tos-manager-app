package com.example.tosmanager.util;

import android.content.Context;
import android.util.TypedValue;

public class Metrics {
    public static int dp(float value, Context context) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics()));
    }
}
