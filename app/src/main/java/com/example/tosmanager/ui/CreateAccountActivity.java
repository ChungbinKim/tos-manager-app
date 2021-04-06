package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tosmanager.R;
import com.example.tosmanager.model.dbhelper;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.CreateAccountViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = CreateAccountActivity.class.getName();
    private CreateAccountViewModel viewModel;
    // UI
    private Button createAccountButton;
    dbhelper helper;

    TextInputEditText createAccountEmail;
    TextInputEditText createAccountPassword;
    TextInputEditText createAccountPasswordConfirm;

    TextView createAccountNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // DB객체
        helper=new dbhelper(this);

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        // 이메일 입력창
        createAccountEmail = (TextInputEditText) findViewById(R.id.loginEmail);
        createAccountEmail.addTextChangedListener(new ForwardText(viewModel.getEmail()));

        // 비밀번호 입력창
         createAccountPassword = (TextInputEditText) findViewById(R.id.loginPassword);
         createAccountPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 비밀번호 확인 입력창
        createAccountPasswordConfirm = (TextInputEditText) findViewById(R.id.createAccountPasswordConfirm);
        createAccountPasswordConfirm.addTextChangedListener(new ForwardText(viewModel.getPasswordConfirm()));

        // 계정 생성 버튼
        createAccountButton = (Button) findViewById(R.id.createAccountCreateButton);
        createAccountButton.setOnClickListener(v -> {
            // 계정생성 결과 알림
            viewModel.createAccount(helper, s -> {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                finish();
            }, e -> {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            });
        });

        viewModel.getIsCreatingAccount().observe(this, b -> {
            createAccountButton.setEnabled(!b);
        });

        // 계정생성 주의사항
        createAccountNotice = (TextView) findViewById(R.id.createAccountNotice);
        createAccountNotice.setText(createNotice());
        createAccountNotice.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // 계정생성 주의사항 텍스트 생성
    private CharSequence createNotice() {
        String[] strings = getResources().getStringArray(R.array.text_privacy_policy_description);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < strings.length; i++) {
            SpannableString str = new SpannableString(strings[i]);

            if (i == 1) {
                // 링크 생성
                str.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {

                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(getResources().getColor(R.color.design_default_color_primary));
                        ds.bgColor = 0xffffffff;
                        ds.setUnderlineText(false);
                    }
                }, 0, strings[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            builder.append(str);
        }
        return builder;
    }
}