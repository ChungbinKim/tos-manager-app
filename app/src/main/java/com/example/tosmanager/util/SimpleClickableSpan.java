package com.example.tosmanager.util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import java.util.concurrent.Callable;

public class SimpleClickableSpan extends ClickableSpan {
    private int color;
    private Consumer<View> onClickCallback;

    public SimpleClickableSpan(int color, Consumer<View> onClick) {
        this.color = color;
        this.onClickCallback = onClick;
    }

    @Override
    public void onClick(@NonNull View v) {
        onClickCallback.accept(v);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(color);
        ds.bgColor = 0xffffffff;
        ds.setUnderlineText(false);
    }
}
