package com.example.tosmanager.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tosmanager.R;

public class MyTosFragment extends Fragment {
    private static final String TAG = MyTosFragment.class.getName();
    private SharedPreferences preferences;
    // UI
    private RecyclerView myTosList;
    private ImageView toggleLayout;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    public MyTosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tos, container, false);

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        gridLayoutManager = new GridLayoutManager(view.getContext(), 3);

        // list/grid view 변환 버튼
        toggleLayout = view.findViewById(R.id.myTosLayout);
        toggleLayout.setOnClickListener(v -> {
            preferences.edit().putBoolean("isGridLayout", !isGridLayout()).apply();
            updateLayout();
        });

        // 약관 목록
        myTosList = view.findViewById(R.id.myTosList);
        myTosList.setAdapter(new TosListAdapter(new String[]{"서비스 A", "서비스 B", "C", "D"}));
        updateLayout();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.my_tos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void updateLayout() {
        RecyclerView.LayoutManager layout = linearLayoutManager;
        @DrawableRes int iconID = R.drawable.ic_baseline_view_module_24;
        if (isGridLayout()) {
            layout = gridLayoutManager;
            iconID = R.drawable.ic_baseline_view_list_24;
        }

        myTosList.setLayoutManager(layout);
        toggleLayout.setImageResource(iconID);
    }

    private boolean isGridLayout() {
        return preferences.getBoolean("isGridLayout", false);
    }
}