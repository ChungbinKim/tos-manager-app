package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
    dbhelper helper;
    SQLiteDatabase db;

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
            viewModel.createAccount(s -> {
                //회원가입
                db = helper.getReadableDatabase();

                String email = createAccountEmail.getText().toString();
                String password = createAccountPassword.getText().toString();
                String pwdconfirm = createAccountPasswordConfirm.getText().toString();
                String sql = "select * from user where email = '"+email+"'";
                Cursor cursor = db.rawQuery(sql, null);

                if(password.equals(pwdconfirm)){
                    if (cursor.getCount()==1) {
                        Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.execSQL("INSERT INTO user(email, password) VALUES ('"+email+"','"+password+"');");
                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    cursor.close();
                    db.close();
                }
                else{
                    Toast.makeText(getApplicationContext(), "정보를 다시 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            }, e -> {});
        });
        viewModel.getIsCreatingAccount().observe(this, b -> {
            createAccountButton.setEnabled(!b);
        });

    }
}