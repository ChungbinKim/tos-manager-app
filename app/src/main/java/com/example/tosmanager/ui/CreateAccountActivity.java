package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tosmanager.R;
import com.example.tosmanager.model.dbhelper;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.CreateAccountViewModel;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private CreateAccountViewModel viewModel;
    // UI
    private Button createAccountButton;
    dbhelper helper;

    EditText createAccountEmail;
    EditText createAccountPassword;
    EditText createAccountPasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // DB객체
        helper=new dbhelper(this);

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        // 이메일 입력창
        createAccountEmail = (EditText) findViewById(R.id.createAccountEmail);
        createAccountEmail.addTextChangedListener(new ForwardText(viewModel.getEmail()));

        // 비밀번호 입력창
         createAccountPassword = (EditText) findViewById(R.id.createAccountPassword);
         createAccountPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 비밀번호 확인 입력창
        createAccountPasswordConfirm = (EditText) findViewById(R.id.createAccountPasswordConfirm);
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