package com.example.tosmanager.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.example.tosmanager.model.ExtraName;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginViewModel viewModel;
    private SharedPreferences sharedPreferences;

    // UI elements
    private Button loginButton;
    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;
    private TextView createAccount;
    private TextView recoverPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        getSupportActionBar().hide();

        // 로그인 세션이 남아있을시 생략
        viewModel.fetchSession().subscribe(session -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }, e -> {
            if (e instanceof TimeoutException) {
                setUpView();
            }
        }, this::setUpView);

        String inputText = getIntent().getStringExtra(ExtraName.INPUT_TEXT);
        if (inputText != null) {
            viewModel.getInputText().setValue(inputText);
        }
    }

    private void setUpView() {
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // 이메일 입력창
        loginEmail = findViewById(R.id.loginEmail);
        loginEmail.addTextChangedListener(new ForwardText(viewModel.getID()));
        loginEmail.requestFocus();

        // 비밀번호 입력창
        loginPassword = findViewById(R.id.loginPassword);
        loginPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 로그인 버튼
        loginButton = findViewById(R.id.loginButton);
        // 계정 생성
        createAccount = findViewById(R.id.loginCreateAccount);
        // 계정 복구
        recoverPassword = findViewById(R.id.loginRecoverPassword);

        viewModel.getIsLogging().observe(this, b -> {
            // 로그인중 버튼 비활성화
            loginButton.setEnabled(!b);
            createAccount.setEnabled(!b);
            recoverPassword.setEnabled(!b);
        });
    }

    // 계정 생성
    public void onCreateAccount(View v) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    // 로그인
    public void onLogIn(View v) {
        Queue<String> queue = new ArrayDeque<>();

        viewModel.logIn(this).subscribe(s -> queue.add(s), e -> {
            // 로그인 실패
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage(e.getMessage())
                    .setNegativeButton("다시 시도",null)
                    .create()
                    .show();
        }, () -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Email", queue.poll());
            intent.putExtra("Password",queue.poll());

            String inputText = viewModel.getInputText().getValue();
            if (inputText != null) {
                intent.putExtra(ExtraName.INPUT_TEXT, inputText);
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            LoginActivity.this.startActivity(intent);
            finish();
        });
    }

    // 비밀번호 찾기
    public void onRecoverPassword(View v) {
        Intent intent = new Intent(this, RecoverPasswordActivity.class);
        startActivity(intent);
    }
}