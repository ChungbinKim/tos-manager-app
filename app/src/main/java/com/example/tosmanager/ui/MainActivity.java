package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tosmanager.R;
import com.example.tosmanager.model.ExtraName;
import com.example.tosmanager.viewmodel.LoginViewModel;
import com.example.tosmanager.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // 하단 메뉴
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            viewModel.getItemID().setValue(item.getItemId());
            return true;
        });

        viewModel.getItemID().observe(this, id -> {
            navigateTo(viewModel.getFragment());
            getSupportActionBar().setTitle(getString(viewModel.getTitleStringName()));
        });

        String inputText = getIntent().getStringExtra(ExtraName.INPUT_TEXT);
        if (inputText != null) {
            Intent intent = new Intent(this, AddTosActivity.class);
            intent.putExtra(ExtraName.INPUT_TEXT, inputText);
            startActivity(intent);
        }
    }

    private void navigateTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}