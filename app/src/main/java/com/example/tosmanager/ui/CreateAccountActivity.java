package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.tosmanager.R;
import com.example.tosmanager.model.dbhelper;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.CreateAccountViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private CreateAccountViewModel viewModel;
    // UI
    private Button createAccountButton;
    dbhelper helper;

    TextInputEditText createAccountEmail;
    TextInputEditText createAccountPassword;
    TextInputEditText createAccountPasswordConfirm;
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
    }
}