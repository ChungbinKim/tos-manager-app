package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.example.tosmanager.model.ExtraName;
import com.example.tosmanager.util.CreateDialog;
import com.google.android.material.textfield.TextInputEditText;

public class AddTosActivity extends AppCompatActivity {
    private LinearLayout tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tos);
        getSupportActionBar().setTitle(R.string.title_input_tos);

        // 설명 text
        TextView textInputByShare = findViewById(R.id.addTosInputByShare);
        textInputByShare.setText(Html.fromHtml(getString(R.string.text_input_tos_by_share)));

        // 공유된 text 처리
        String inputText = getIntent().getStringExtra(ExtraName.INPUT_TEXT);
        if (inputText != null) {
            TextInputEditText input = findViewById(R.id.addTosInput);
            input.setText(inputText);
        }

        // 아래 설명 부분
        tutorial = findViewById(R.id.addTosTutorial);
        int tutorialVisibility = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("input_tutorial", true) ? View.VISIBLE : View.GONE;
        tutorial.setVisibility(tutorialVisibility);

        Button summarizeButton = findViewById(R.id.addTosButton);
        summarizeButton.setOnClickListener(v -> {
            CreateDialog.createPrompt(this, R.string.text_summarize_dialog_message, (dialog, which) -> {
                Intent i = new Intent(this, TosDetailsActivity.class);
                startActivity(i);
            }).show();
        });
    }
}