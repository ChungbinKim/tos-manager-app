package com.example.tosmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
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

    public Disposable createAccount(Consumer<String> onNext, Consumer<Throwable> onError) {
        isCreatingAccount.setValue(true);
        // TODO: Observable.just 대신 입력한 정보로 계정 생성 요청
        return Observable.just(email.getValue() + password.getValue() + passwordConfirm.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> isCreatingAccount.postValue(false))
                .subscribe(onNext, onError);
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
