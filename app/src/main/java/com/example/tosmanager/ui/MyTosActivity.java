package com.example.tosmanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tosmanager.R;
import com.example.tosmanager.model.DataHolder;
import com.example.tosmanager.model.LoginSession;

public class MyTosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tos);

        // 아직은 로그인 결과만 출력
        TextView t = (TextView) findViewById(R.id.tokenTest);
        LoginSession session = DataHolder.getInstace().getLoginSession();
        if (session != null) {
            t.setText("Token: " + DataHolder.getInstace().getLoginSession().getToken());
        } else {
            t.setText("비로그인");
        }
    }
}