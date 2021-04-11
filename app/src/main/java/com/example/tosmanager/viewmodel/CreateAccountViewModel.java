package com.example.tosmanager.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.tosmanager.model.dbhelper;
import com.example.tosmanager.ui.CreateAccountActivity;
import com.example.tosmanager.ui.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateAccountViewModel extends ViewModel {
    private final int MINIMUM_PASSWORD_LENGTH = 4;
    // UI
    private final MutableLiveData<String> email = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    private final MutableLiveData<String> passwordConfirm = new MutableLiveData<>("");
    // state
    private final MutableLiveData<Boolean> isCreatingAccount = new MutableLiveData<>(false);

    public MutableLiveData<String> getEmail() {
        return email;
    }
    public MutableLiveData<String> getPassword() {
        return password;
    }
    public MutableLiveData<String> getPasswordConfirm() {
        return passwordConfirm;
    }
    public MutableLiveData<Boolean> getIsCreatingAccount() {
        return isCreatingAccount;
    }

    public Observable<String> createAccount(Context context) {
        isCreatingAccount.setValue(true);

        String Email = this.email.getValue();
        String Passsword = this.password.getValue();
        String checkpwd = this.passwordConfirm.getValue();

        Observable<String> observable = Observable.create(emitter -> {
            if (!Passsword.equals(checkpwd)) {
                emitter.onError(new Throwable("비밀번호를 일치시켜주세요"));
                return;
            }
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        emitter.onNext("회원가입성공");
                    } else {
                        emitter.onError(new Throwable("회원가입실패"));
                    }
                } catch (JSONException e) {
                    emitter.onError(e);
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(Email, Passsword, responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(registerRequest);
        });

        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> isCreatingAccount.postValue(false));
    }

    // 이메일 형식 간략 검증
    public boolean isEmailValid() {
        return email.getValue().matches("^[a-zA-Z0-9]+@[a-zA-Z]+(.[a-zA-Z]{2,3}){1,3}$");
    }

    // 비밀번호 확인 검증
    public boolean isPasswordTypedCorrectly() {
        return password.getValue().equals(passwordConfirm.getValue());
    }

    // 비밀번호 요구 길이
    public boolean isPasswordLongEnough() {
        return password.getValue().length() >= MINIMUM_PASSWORD_LENGTH;
    }
}
