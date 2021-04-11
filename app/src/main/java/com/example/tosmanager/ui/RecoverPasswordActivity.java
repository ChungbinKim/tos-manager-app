package com.example.tosmanager.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.tosmanager.R;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.RecoverPasswordViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class RecoverPasswordActivity extends AppCompatActivity {
    private RecoverPasswordViewModel viewModel;
    // UI
    private Button recoverButton;
    TextInputEditText emailInput;

    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        viewModel = new ViewModelProvider(this).get(RecoverPasswordViewModel.class);

        emailInput = (TextInputEditText) findViewById(R.id.recoverPasswordEmail);

        // 비밀번호 찾기 버튼
        recoverButton = findViewById(R.id.recoverPasswordButton);
        recoverButton.setOnClickListener(v -> {
            viewModel.recoverPassword(s -> {
                Email = emailInput.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                //회원정보 존재
                                Toast.makeText(getApplicationContext(),"해당 email을 확인하세요", Toast.LENGTH_LONG).show();
                            }
                            else {
                                //로그인 실패
                                AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPasswordActivity.this);
                                builder.setMessage("일치하는 회원정보가 없습니다.")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }

                        } catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RecoverRequest recoverRequest = new RecoverRequest(Email,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RecoverPasswordActivity.this);
                queue.add(recoverRequest);
            }, e -> {});
        });

        // 이메일 입력
        TextInputEditText emailInput = findViewById(R.id.recoverPasswordEmail);
        emailInput.addTextChangedListener(new ForwardText(viewModel.getEmail()));

        viewModel.getIsRequesting().observe(this, b -> {
            recoverButton.setEnabled(!b);
        });
    }
}