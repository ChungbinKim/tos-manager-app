package com.example.tosmanager.ui;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tosmanager.R;

// ViewHolder
public class TosViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;

    public TosViewHolder(View view) {
        super(view);
        textView = view.findViewById(R.id.tosItemName);
    }

    public TextView getTextView() {
        return textView;
    }
}
