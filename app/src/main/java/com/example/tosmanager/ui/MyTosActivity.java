package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.example.tosmanager.model.DataHolder;
import com.example.tosmanager.model.LoginSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyTosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tos);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        bottomNav.setSelectedItemId(R.id.navigationMyTos);
        navigateTo(new MyTosFragment());
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.navigationMyTos:
                selectedFragment = new MyTosFragment();
                break;
            case R.id.navigationCalendar:
                selectedFragment = new CalendarFragment();
                break;
            case R.id.navigationConfiguration:
                selectedFragment = new ConfigurationFragment();
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