package com.example.tosmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecoverPasswordViewModel extends ViewModel {
    // UI
    private final MutableLiveData<String> email = new MutableLiveData<>("");
    // state
    private final MutableLiveData<Boolean> isRequesting = new MutableLiveData<>(false);

    public MutableLiveData<String> getEmail() {
        return email;
    }
    public MutableLiveData<Boolean> getIsRequesting() {
        return isRequesting;
    }

    public Disposable recoverPassword(Consumer<String> onNext, Consumer<Throwable> onError) {
        isRequesting.setValue(true);
        // TODO Observable.just 대신 비밀번호 찾기 요청.
        return Observable.just(email.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> isRequesting.postValue(false))
                .subscribe(onNext, onError);
    }
}
