package com.example.tosmanager.util;

import android.content.Context;
import android.util.TypedValue;


public class ColorUtil {
    public static int getThemeColor(int attr, Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }
}
