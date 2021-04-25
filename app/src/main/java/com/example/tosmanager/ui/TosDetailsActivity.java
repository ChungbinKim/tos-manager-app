package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.example.tosmanager.model.ExtraName;
import com.example.tosmanager.util.CreateDialog;
import com.example.tosmanager.viewmodel.TosDetailsViewModel;

import java.util.Date;

public class TosDetailsActivity extends AppCompatActivity {
    private TosDetailsViewModel viewModel;
    private boolean deleteConfirmed;

    // UI
    private TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos_details);
        getSupportActionBar().setTitle(R.string.title_tos_details);

        viewModel = new ViewModelProvider(this).get(TosDetailsViewModel.class);

        String name = getIntent().getStringExtra(ExtraName.TERMS_NAME);
        if (name == null) {
            name = new Date().toString();
        }

        nameText = findViewById(R.id.tosDetailsName);
        nameText.setText(name);

        viewModel.fetchTermsSummary(name).subscribe(summary -> {});
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