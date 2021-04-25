package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView editName;

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
        viewModel.getServiceName().setValue(name);

        // 서비스 이름
        nameText = findViewById(R.id.tosDetailsName);
        viewModel.getServiceName().observe(this, serviceName -> {
            nameText.setText(serviceName);
        });

        // 이름 변경 버튼
        editName = findViewById(R.id.tosDetailsEditName);
        editName.setOnClickListener(v -> {
            final View view = getLayoutInflater().inflate(R.layout.dialog_edit_text, null);
            final EditText editText = view.findViewById(R.id.dialogEditText);
            editText.setText(nameText.getText());

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage(R.string.text_edit_name)
                    .setView(view)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        viewModel.getServiceName().setValue(editText.getText().toString());
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());

            builder.create().show();
        });

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