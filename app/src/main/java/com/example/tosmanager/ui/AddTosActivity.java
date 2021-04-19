package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddTosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tos);

        // 설명 text
        TextView textInputByShare = findViewById(R.id.addTosInputByShare);
        textInputByShare.setText(Html.fromHtml(getString(R.string.text_input_tos_by_share)));

        // 공유된 text 처리
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action) && intent.getType().equals("text/plain")) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (text != null) {
                TextInputEditText input = findViewById(R.id.addTosInput);
                input.setText(text);
            }
        }
    }
}