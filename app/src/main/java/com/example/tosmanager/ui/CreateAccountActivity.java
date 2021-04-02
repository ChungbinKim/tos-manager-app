package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tosmanager.R;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.CreateAccountViewModel;
import com.example.tosmanager.viewmodel.LoginViewModel;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private CreateAccountViewModel viewModel;
    // UI
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        // 이메일 입력창
        EditText createAccountEmail = (EditText) findViewById(R.id.createAccountEmail);
        createAccountEmail.addTextChangedListener(new ForwardText(viewModel.getEmail()));

        // 비밀번호 입력창
        EditText createAccountPassword = (EditText) findViewById(R.id.createAccountPassword);
        createAccountPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 비밀번호 확인 입력창
        EditText createAccountPasswordConfirm = (EditText) findViewById(R.id.createAccountPasswordConfirm);
        createAccountPasswordConfirm.addTextChangedListener(new ForwardText(viewModel.getPasswordConfirm()));

        // 계정 생성 버튼
        createAccountButton = (Button) findViewById(R.id.createAccountCreateButton);
        createAccountButton.setOnClickListener(v -> {
            viewModel.createAccount(s -> {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                finish();
            }, e -> {});
        });
        viewModel.getIsCreatingAccount().observe(this, b -> {
            createAccountButton.setEnabled(!b);
        });
    }
}