package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.example.tosmanager.R;

public class InitialNoticeActivity extends AppCompatActivity {
    // UI
    private TextView initialNoticeText;
    private Button initialNoticeAgreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_notice);

        // 고지 텍스트
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString s = new SpannableString(getString(R.string.text_notice));
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(s);

        initialNoticeText = (TextView) findViewById(R.id.initialNoticeText);
        initialNoticeText.setText(builder);

        // 동의 버튼
        initialNoticeAgreeButton = (Button) findViewById(R.id.initialNoticeAgreeButton);
        initialNoticeAgreeButton.setEnabled(false);
        initialNoticeAgreeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });
    }
}