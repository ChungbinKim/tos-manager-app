package com.example.tosmanager.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tosmanager.BuildConfig;
import com.example.tosmanager.R;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.InitialNoticeViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.ms.square.android.expandabletextview.ExpandableTextView;

public class InitialNoticeActivity extends AppCompatActivity {
    private InitialNoticeViewModel viewModel;
    // UI
    private Button initialNoticeAgreeButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_notice);

        viewModel = new ViewModelProvider(this).get(InitialNoticeViewModel.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().hide();

        if (sharedPreferences.getBoolean("is-agreed", false)) {
            proceed();
            return;
        }

        // 고지 텍스트
        TextView initialNoticeText = findViewById(R.id.initialNoticeText);
        initialNoticeText.setText(Html.fromHtml(getString(R.string.text_notice)));

        // 사용 사례 텍스트
        ExpandableTextView useCases = findViewById(R.id.InitialNoticeUseCases);
        useCases.setText(Html.fromHtml(getString(R.string.text_notice_use_cases)));
        // 동의 텍스트
        TextView lastText = findViewById(R.id.initialNoticeTextLast);
        lastText.setText(Html.fromHtml(getString(R.string.text_notice_last)));
        // 동의 텍스트창
        TextInputEditText initialNoticeTextField = findViewById(R.id.initialNoticeTextField);
        initialNoticeTextField.addTextChangedListener(new ForwardText(viewModel.getConfirmText()));

        // 동의 버튼
        initialNoticeAgreeButton = findViewById(R.id.initialNoticeAgreeButton);
        initialNoticeAgreeButton.setOnClickListener(v -> {
            proceed();
        });

        viewModel.getConfirmText().observe(this, s -> {
            initialNoticeAgreeButton.setEnabled(viewModel.isConfirmed());
        });
    }

    private void proceed() {
        sharedPreferences.edit().putBoolean("is-agreed", true).apply();

        Intent intent;
        if (sharedPreferences.getBoolean("isAccountless", false)) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }
}