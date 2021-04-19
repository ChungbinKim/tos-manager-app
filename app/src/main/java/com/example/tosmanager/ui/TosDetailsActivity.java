package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tosmanager.R;
import com.example.tosmanager.util.CreateDialog;

public class TosDetailsActivity extends AppCompatActivity {
    private boolean deleteConfirmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos_details);
        getSupportActionBar().setTitle(R.string.title_tos_details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tos_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        deleteConfirmed = false;
        if (item.getItemId() == R.id.deleteTos) {
            CreateDialog.createPrompt(this, R.string.delete_tos_dialog_message, (dialog, which) -> {
                deleteConfirmed = true;
            }).show();
        }
        return deleteConfirmed;
    }
}