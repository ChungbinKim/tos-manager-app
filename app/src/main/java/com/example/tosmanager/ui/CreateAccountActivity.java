package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tosmanager.R;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.util.SimpleClickableSpan;
import com.example.tosmanager.viewmodel.CreateAccountViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = CreateAccountActivity.class.getName();
    private CreateAccountViewModel viewModel;
    // UI
    private Button createAccountButton;
    private TextInputEditText createAccountEmail;
    private TextInputEditText createAccountPassword;
    private TextInputEditText createAccountPasswordConfirm;

    private TextView createAccountNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        // 이메일 입력창
        createAccountEmail = findViewById(R.id.loginEmail);
        createAccountEmail.addTextChangedListener(new ForwardText(viewModel.getEmail()));

        // 비밀번호 입력창
         createAccountPassword = findViewById(R.id.loginPassword);
         createAccountPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 비밀번호 확인 입력창
        createAccountPasswordConfirm = findViewById(R.id.createAccountPasswordConfirm);
        createAccountPasswordConfirm.addTextChangedListener(new ForwardText(viewModel.getPasswordConfirm()));

        // 계정 생성 버튼
        createAccountButton = findViewById(R.id.createAccountCreateButton);
        createAccountButton.setOnClickListener(v -> {
            viewModel.createAccount(this).subscribe(s -> {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                finish();
            }, e -> {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            });
        });

        // 계정생성 주의사항
        createAccountNotice = findViewById(R.id.createAccountNotice);
        createAccountNotice.setText(createNotice());
        createAccountNotice.setMovementMethod(LinkMovementMethod.getInstance());

        viewModel.getIsCreatingAccount().observe(this, b -> {
            createAccountButton.setEnabled(!b);
            createAccountNotice.setEnabled(!b);
        });
    }

    // 계정생성 주의사항 텍스트 생성
    private CharSequence createNotice() {
        String[] strings = getResources().getStringArray(R.array.text_privacy_policy_description);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < strings.length; i++) {
            SpannableString str = new SpannableString(strings[i]);

            if (i == 1) {
                // 링크 생성
                int color = getResources().getColor(R.color.design_default_color_primary);
                str.setSpan(new SimpleClickableSpan(color, v -> {
                    Intent intent = new Intent(this, PrivacyPolicyActivity.class);
                    startActivity(intent);
                }), 0, strings[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            builder.append(str);
        }
        return builder;
    }
}