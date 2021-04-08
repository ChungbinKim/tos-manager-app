package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.tosmanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.navigation_my_tos);

        bottomNav.setSelectedItemId(R.id.navigationMyTos);
        navigateTo(new MyTosFragment());
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.navigationMyTos:
                selectedFragment = new MyTosFragment();
                actionBar.setTitle(R.string.navigation_my_tos);
                break;
            case R.id.navigationCalendar:
                selectedFragment = new CalendarFragment();
                actionBar.setTitle(R.string.navigation_calendar);
                break;
            case R.id.navigationConfiguration:
                selectedFragment = new ConfigurationFragment();
                actionBar.setTitle(R.string.navigation_configuration);
                break;
        }

        navigateTo(selectedFragment);
        return true;
    }

    private void navigateTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}