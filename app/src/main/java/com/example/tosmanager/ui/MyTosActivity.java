package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }
}