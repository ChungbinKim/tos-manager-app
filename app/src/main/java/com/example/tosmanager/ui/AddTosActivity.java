package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.example.tosmanager.model.ExtraName;
import com.example.tosmanager.util.CreateDialog;
import com.google.android.material.textfield.TextInputEditText;

public class AddTosActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    // UI
    private CheckBox doNotShowAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tos);
        getSupportActionBar().setTitle(R.string.title_input_tos);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

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
        LinearLayout tutorial = findViewById(R.id.addTosTutorial);
        int tutorialVisibility = sharedPreferences.getBoolean("input_tutorial", true) ? View.VISIBLE : View.GONE;
        tutorial.setVisibility(tutorialVisibility);

        Button summarizeButton = findViewById(R.id.addTosButton);
        summarizeButton.setOnClickListener(v -> {
            CreateDialog.createPrompt(this, R.string.text_summarize_dialog_message, (dialog, which) -> {
                Intent i = new Intent(this, TosDetailsActivity.class);
                startActivity(i);
            }).show();
        });

        doNotShowAgain = findViewById(R.id.addTosDoNotShowAgain);
    }

    @Override
    protected void onDestroy() {
        // 설명 다시 보지 않기 반영
        if (doNotShowAgain.isChecked()) {
            sharedPreferences.edit().putBoolean("input_tutorial", false).apply();
        }
        super.onDestroy();
    }
}