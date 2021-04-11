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
import com.example.tosmanager.util.CreateDialog;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.viewmodel.RecoverPasswordViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class RecoverPasswordActivity extends AppCompatActivity {
    private RecoverPasswordViewModel viewModel;
    // UI
    private Button recoverButton;
    TextInputEditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        viewModel = new ViewModelProvider(this).get(RecoverPasswordViewModel.class);

        emailInput = (TextInputEditText) findViewById(R.id.recoverPasswordEmail);

        // 비밀번호 찾기 버튼
        recoverButton = findViewById(R.id.recoverPasswordButton);
        recoverButton.setOnClickListener(v -> {
            viewModel.recoverPassword(this).subscribe(s -> {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }, e -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPasswordActivity.this);
                builder.setMessage(e.getMessage())
                        .setNegativeButton("다시 시도",null)
                        .create()
                        .show();
            });
        });

        // 이메일 입력
        TextInputEditText emailInput = findViewById(R.id.recoverPasswordEmail);
        emailInput.addTextChangedListener(new ForwardText(viewModel.getEmail()));

        viewModel.getIsRequesting().observe(this, b -> {
            recoverButton.setEnabled(!b);
        });
    }
}