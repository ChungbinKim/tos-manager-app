package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tosmanager.R;
import com.example.tosmanager.model.dbhelper;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginViewModel viewModel;
    dbhelper helper;

    // UI elements
    private Button skipButton;
    TextInputEditText loginEmail;
    TextInputEditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setUpObservers();
        getSupportActionBar().hide();

        // DB객체
        helper=new dbhelper(this);

        // 이메일 입력창
        loginEmail = findViewById(R.id.loginEmail);
        loginEmail.addTextChangedListener(new ForwardText(viewModel.getID()));

        // 비밀번호 입력창
        loginPassword = findViewById(R.id.loginPassword);
        loginPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 건너뛰기
        skipButton = findViewById(R.id.loginSkipButton);
    }

    // 계정 생성
    public void onCreateAccount(View v) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    // 로그인
    public void onLogIn(View v) {
        viewModel.logIn(helper, s -> {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }, e -> {
            // 로그인 실패
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d(TAG, e.toString());
        });
    }

    // 건너뛰기
    public void onSkipLogIn(View v) {
        viewModel.skipLogIn();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // 비밀번호 찾기
    public void onRecoverPassword(View v) {
        Intent intent = new Intent(this, RecoverPasswordActivity.class);
        startActivity(intent);
    }

    private void setUpObservers() {
        viewModel.getIsLogging().observe(this, b -> {
            skipButton.setEnabled(!b);
        });
    }
}