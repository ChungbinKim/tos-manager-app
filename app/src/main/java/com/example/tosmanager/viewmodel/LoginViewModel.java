package com.example.tosmanager.viewmodel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tosmanager.model.DataHolder;
import com.example.tosmanager.model.LoginSession;
import com.example.tosmanager.model.dbhelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private static final String TAG = LoginViewModel.class.getName();
    // UI
    private final MutableLiveData<String> id = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    // state
    private final MutableLiveData<Boolean> isLogging = new MutableLiveData<>(false);
    SQLiteDatabase db;

    public MutableLiveData<String> getID() {
        return id;
    }
    public MutableLiveData<String> getPassword() {
        return password;
    }
    public MutableLiveData<Boolean> getIsLogging() {
        return isLogging;
    }

    public Disposable logIn(Consumer<String> onNext, Consumer<Throwable> onError) {
        isLogging.setValue(true);
        return processLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> isLogging.postValue(false))
                .subscribe(s -> {
                    // 가짜 token으로 login
                    LoginSession session = new LoginSession(s);
                    DataHolder.getInstace().setLoginSession(session);

                    onNext.accept(s);
                }, onError);
    }

    public void skipLogIn() {
        DataHolder.getInstace().setLoginSession(null);
    }

    private Observable<String> processLogin() {
        // TODO: Observable.just 대신 원격 로그인 요청. 성공하면 실제 token, 실패하면 exception
        return Observable.just(id.getValue() + password.getValue());
    }
}
