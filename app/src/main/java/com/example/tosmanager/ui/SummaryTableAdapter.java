package com.example.tosmanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.example.tosmanager.R;
import com.example.tosmanager.util.IconString;

public class SummaryTableAdapter extends AbstractTableAdapter<CharSequence, CharSequence, CharSequence> {
    class CellViewHolder extends AbstractViewHolder {
        final LinearLayout container;
        final TextView textView;

        public CellViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.cell_container);
            textView = itemView.findViewById(R.id.cell_data);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_table_cell, parent, false);
        return new CellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable CharSequence cellItemModel, int columnPosition, int rowPosition) {
        CellViewHolder viewHolder = (CellViewHolder) holder;
        LinearLayout container = viewHolder.container;
        TextView textView = viewHolder.textView;

        CharSequence cell = cellItemModel;
        if (cell.equals("false")) {
            cell = new IconString(container.getContext(), R.drawable.ic_baseline_close_24, container.getResources().getColor(R.color.red));
        } else if (cell.equals("true")) {
            cell = new IconString(container.getContext(), R.drawable.ic_baseline_check_24, container.getResources().getColor(R.color.green));
        }

        textView.setText(cell);

        container.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        textView.requestLayout();
    }

    class ColumnHeaderViewHolder extends AbstractViewHolder {
        final LinearLayout container;
        final TextView textView;

        public ColumnHeaderViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.column_header_container);
            textView = itemView.findViewById(R.id.column_header_textView);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_table_column_header, parent, false);
        return new ColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable CharSequence columnHeaderItemModel, int columnPosition) {
        CharSequence columnHeader = columnHeaderItemModel;

        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.textView.setText(columnHeader);

        columnHeaderViewHolder.container.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        columnHeaderViewHolder.textView.requestLayout();
    }

    class RowHeaderViewHolder extends AbstractViewHolder {
        final TextView textView;

        public RowHeaderViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.row_header_textView);
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_table_row_header, parent, false);
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable CharSequence rowHeaderItemModel, int rowPosition) {
        CharSequence rowHeader = rowHeaderItemModel;

        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.textView.setText(rowHeader);
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_table_corner, parent, false);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }
}
