package com.example.tosmanager.ui;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tosmanager.R;
import com.example.tosmanager.model.SearchResultItem;

import java.util.ArrayList;

public class TosListAdapter extends RecyclerView.Adapter<TosViewHolder> {
    @LayoutRes private int layoutID;
    @ColorInt private int color;
    private ArrayList<SearchResultItem> dataSet;

    public TosListAdapter(int layoutID, ArrayList<SearchResultItem> dataSet, @ColorInt int color) {
        this.layoutID = layoutID;
        updateDataSet(dataSet);
        this.color = color;
    }

    public void updateDataSet(ArrayList<SearchResultItem> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public TosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutID, viewGroup, false);

        return new TosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TosViewHolder tosViewHolder, final int position) {
        TextView text = tosViewHolder.getTextView();
        SearchResultItem item = dataSet.get(position);

        Spannable spannable = new SpannableStringBuilder(item.getServiceName());
        for (Pair<Integer, Integer> range : item.getHighlightRanges()) {
            spannable.setSpan(new ForegroundColorSpan(color), range.first, range.second, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text.setText(spannable);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

