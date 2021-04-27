package com.example.tosmanager.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

public class IconString extends SpannableString {
    public IconString(Context context, @DrawableRes int iconID, @ColorInt int iconColor) {
        super("a");
        Drawable d = ContextCompat.getDrawable(context, iconID);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.setTint(iconColor);
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
