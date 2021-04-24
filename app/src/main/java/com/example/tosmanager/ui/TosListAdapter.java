package com.example.tosmanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tosmanager.R;

import java.util.ArrayList;

public class TosListAdapter extends RecyclerView.Adapter<TosViewHolder> {
    @LayoutRes private int layoutID;
    private ArrayList<CharSequence> localDataSet;

    public TosListAdapter(int layoutID, ArrayList<CharSequence> dataSet) {
        this.layoutID = layoutID;
        localDataSet = dataSet;
    }

    @Override
    public TosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutID, viewGroup, false);

        return new TosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TosViewHolder tosViewHolder, final int position) {
        tosViewHolder.getTextView().setText(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

