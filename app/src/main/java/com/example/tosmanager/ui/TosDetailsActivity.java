package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tosmanager.R;

public class TosDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos_details);
        getSupportActionBar().setTitle(R.string.title_tos_details);
    }
}