package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.CreateAccountViewModel;
import com.example.tosmanager.viewmodel.InitialNoticeViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class InitialNoticeActivity extends AppCompatActivity {
    private InitialNoticeViewModel viewModel;
    // UI
    private TextView initialNoticeText;
    private Button initialNoticeAgreeButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_notice);

        viewModel = new ViewModelProvider(this).get(InitialNoticeViewModel.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isAgreed = sharedPreferences.getBoolean("is-agreed", false);
        if (isAgreed) {
            proceed();
            return;
        }

        // 고지 텍스트
        initialNoticeText = (TextView) findViewById(R.id.initialNoticeText);
        initialNoticeText.setText(Html.fromHtml(getString(R.string.text_notice)));

        // 동의 텍스트창
        TextInputEditText initialNoticeTextField = (TextInputEditText) findViewById(R.id.initialNoticeTextField);
        initialNoticeTextField.addTextChangedListener(new ForwardText(viewModel.getConfirmText()));

        // 동의 버튼
        initialNoticeAgreeButton = (Button) findViewById(R.id.initialNoticeAgreeButton);
        initialNoticeAgreeButton.setOnClickListener(v -> {
            proceed();
        });

        viewModel.getConfirmText().observe(this, s -> {
            initialNoticeAgreeButton.setEnabled(viewModel.isConfirmed());
        });
    }

    private void proceed() {
        sharedPreferences.edit().putBoolean("is-agreed", true).apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}