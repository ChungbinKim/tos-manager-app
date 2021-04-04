package com.example.tosmanager.viewmodel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tosmanager.model.dbhelper;

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

    SQLiteDatabase db;

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

    public Disposable createAccount(dbhelper helper, Consumer<String> onNext, Consumer<Throwable> onError) {
        isCreatingAccount.setValue(true);
        return processCreateAccount(helper)
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

    private Observable<String> processCreateAccount(dbhelper helper) {
        //회원가입
        db = helper.getReadableDatabase();

        String email = this.email.getValue();
        String password = this.password.getValue();
        String pwdconfirm = this.passwordConfirm.getValue();
        String sql = "select * from user where email = '"+email+"'";
        Cursor cursor = db.rawQuery(sql, null);

        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            if(password.equals(pwdconfirm)){
                if (cursor.getCount()==1) {
                    emitter.onError(new Throwable("이미 존재하는 아이디입니다."));
                }
                else {
                    db.execSQL("INSERT INTO user(email, password) VALUES ('"+email+"','"+password+"');");
                    emitter.onNext("회원가입 성공");
                }
                cursor.close();
                db.close();
            }
            else{
                emitter.onError(new Throwable("정보를 다시 입력해주세요."));
            }
            emitter.onComplete();
        });
    }
}
