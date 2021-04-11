package com.example.tosmanager.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.tosmanager.R;
import com.example.tosmanager.model.dbhelper;
import com.example.tosmanager.util.ForwardText;
import com.example.tosmanager.util.SimpleClickableSpan;
import com.example.tosmanager.viewmodel.CreateAccountViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = CreateAccountActivity.class.getName();
    private CreateAccountViewModel viewModel;
    // UI
    private Button createAccountButton;
    TextInputEditText createAccountEmail;
    TextInputEditText createAccountPassword;
    TextInputEditText createAccountPasswordConfirm;

    String Email;
    String Passsword;
    String checkpwd;

    TextView createAccountNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        // 이메일 입력창
        createAccountEmail = findViewById(R.id.loginEmail);
        createAccountEmail.addTextChangedListener(new ForwardText(viewModel.getEmail()));

        // 비밀번호 입력창
         createAccountPassword = findViewById(R.id.loginPassword);
         createAccountPassword.addTextChangedListener(new ForwardText(viewModel.getPassword()));

        // 비밀번호 확인 입력창
        createAccountPasswordConfirm = findViewById(R.id.createAccountPasswordConfirm);
        createAccountPasswordConfirm.addTextChangedListener(new ForwardText(viewModel.getPasswordConfirm()));

        // 계정 생성 버튼
        createAccountButton = findViewById(R.id.createAccountCreateButton);
        createAccountButton.setOnClickListener(v -> {
            Email = createAccountEmail.getText().toString();
            Passsword = createAccountPassword.getText().toString();
            checkpwd = createAccountPasswordConfirm.getText().toString();

            viewModel.createAccount(s -> {
                if (!Passsword.equals(checkpwd)) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 일치시켜주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원가입성공", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입실패", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                RegisterRequest registerRequest = new RegisterRequest(Email, Passsword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateAccountActivity.this);
                queue.add(registerRequest);
            }, e -> {
            });
        });

        viewModel.getIsCreatingAccount().observe(this, b -> {
            createAccountButton.setEnabled(!b);
        });

        // 계정생성 주의사항
        createAccountNotice = findViewById(R.id.createAccountNotice);
        createAccountNotice.setText(createNotice());
        createAccountNotice.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // 계정생성 주의사항 텍스트 생성
    private CharSequence createNotice() {
        String[] strings = getResources().getStringArray(R.array.text_privacy_policy_description);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < strings.length; i++) {
            SpannableString str = new SpannableString(strings[i]);

            if (i == 1) {
                // 링크 생성
                int color = getResources().getColor(R.color.design_default_color_primary);
                str.setSpan(new SimpleClickableSpan(color, v -> {
                    Intent intent = new Intent(this, PrivacyPolicyActivity.class);
                    startActivity(intent);
                }), 0, strings[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            builder.append(str);
        }
        return builder;
    }
}