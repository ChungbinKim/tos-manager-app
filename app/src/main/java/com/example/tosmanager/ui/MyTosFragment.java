package com.example.tosmanager.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tosmanager.R;
import com.example.tosmanager.viewmodel.MyTosViewModel;

import java.util.ArrayList;

public class MyTosFragment extends Fragment {
    private MyTosViewModel viewModel;
    private static final String TAG = MyTosFragment.class.getName();
    private SharedPreferences preferences;
    // UI
    private RecyclerView myTosList;
    private ImageView toggleLayout;
    private ImageView sortBy;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private TosListAdapter listViewAdapter;
    private TosListAdapter gridViewAdapter;

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

        viewModel = new ViewModelProvider(this).get(MyTosViewModel.class);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        gridLayoutManager = new GridLayoutManager(view.getContext(), 3);

        // list/grid view 변환 버튼
        toggleLayout = view.findViewById(R.id.myTosLayout);
        toggleLayout.setOnClickListener(v -> {
            preferences.edit().putBoolean("isGridLayout", !isGridLayout()).apply();
            updateLayout();
        });
        toggleLayout.setEnabled(false);

        // 정렬 순서 메뉴
        sortBy = view.findViewById(R.id.myTosSortBy);
        sortBy.setOnClickListener(v -> {
            PopupMenu sortMenu = new PopupMenu(view.getContext(), v);
            sortMenu.getMenuInflater().inflate(R.menu.sort_by, sortMenu.getMenu());
            sortMenu.setOnMenuItemClickListener(item -> {
                viewModel.getSortID().setValue(item.getItemId());
                return true;
            });
            sortMenu.show();
        });
        sortBy.setEnabled(false);

        viewModel.getSortID().observe(getViewLifecycleOwner(), id -> {
            updateLayout();
        });

        // 약관 목록
        myTosList = view.findViewById(R.id.myTosList);

        // 데이터 접근
        viewModel.fetchServiceNames().subscribe(s -> {
            viewModel.getServiceNames().add(s);
        }, e -> {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }, () -> {
            listViewAdapter = new TosListAdapter(R.layout.fragment_tos_list_item, viewModel.getServiceNames());
            gridViewAdapter = new TosListAdapter(R.layout.fragment_tos_grid_item, viewModel.getServiceNames());

            updateLayout();
            toggleLayout.setEnabled(true);
            sortBy.setEnabled(true);
        });

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
        viewModel.sortServices();

        RecyclerView.LayoutManager layout = linearLayoutManager;
        RecyclerView.Adapter<TosViewHolder> adapter = listViewAdapter;
        @DrawableRes int iconID = R.drawable.ic_baseline_view_module_24;

        if (isGridLayout()) {
            layout = gridLayoutManager;
            adapter = gridViewAdapter;
            iconID = R.drawable.ic_baseline_view_list_24;
        }

        myTosList.setLayoutManager(layout);
        myTosList.setAdapter(adapter);
        toggleLayout.setImageResource(iconID);
    }

    private boolean isGridLayout() {
        return preferences.getBoolean("isGridLayout", false);
    }
}