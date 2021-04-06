package com.example.tosmanager.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.widget.TextView;

import com.example.tosmanager.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    // UI
    private TextView privacy_policy_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        // 개인정보처리방침
        privacy_policy_text = (TextView) findViewById(R.id.privacyPolicyText);
        privacy_policy_text.setText(Html.fromHtml(getString(R.string.privacy_policy_text)));
    }
}