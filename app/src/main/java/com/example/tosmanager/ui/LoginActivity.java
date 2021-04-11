package com.example.tosmanager.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.tosmanager.R;
import com.example.tosmanager.model.dbhelper;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.Queue;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginViewModel viewModel;

    // UI elements
    private Button skipButton;
    private TextInputEditText loginEmail;
    private TextInputEditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setUpObservers();
        getSupportActionBar().hide();

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

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            LoginActivity.this.startActivity(intent);
            finish();
        });
    }

    // 건너뛰기
    public void onSkipLogIn(View v) {
        viewModel.skipLogIn();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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