package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tosmanager.R;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginViewModel viewModel;
    dbhelper helper;
    SQLiteDatabase db;

    // UI elements
    private Button skipButton;
    EditText loginEmail;
    EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setUpObservers();

        // DB객체
        helper=new dbhelper(this);

        // 이메일 입력창
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginEmail.addTextChangedListener(new ForwardText(viewModel.getID()));

        // 비밀번호 입력창
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 건너뛰기
        skipButton = (Button) findViewById(R.id.loginSkipButton);
    }

    // 계정 생성
    public void onCreateAccount(View v) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    // 로그인
    public void onLogIn(View v) {
        viewModel.logIn(s -> {
            // 로그인 성공시

            db = helper.getReadableDatabase();
            String email = loginEmail.getText().toString();
            String pwd = loginPassword.getText().toString();

            // 저장된 회원정보 존부 확인
            String sql = "select * from user where email = '"+email+"' and password= '"+pwd+"'";
            Cursor cursor = db.rawQuery(sql, null);
            while(cursor.moveToNext()){
                String e = cursor.getString(0);
                String p = cursor.getString(1);
                Log.d("select","email :"+e+" pwd: "+p);
            }

            if(cursor.getCount()==1){
                Toast.makeText(LoginActivity.this, email+"님 환영합니다.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MyTosActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this,"로그인 정보가 틀렸습니다.",Toast.LENGTH_LONG).show();
            }
            cursor.close();
            db.close();

        }, e -> {
            // 로그인 실패
            Log.d(TAG, e.toString());
        });
    }

    // 건너뛰기
    public void onSkipLogIn(View v) {
        viewModel.skipLogIn();
        Intent intent = new Intent(this, MyTosActivity.class);
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