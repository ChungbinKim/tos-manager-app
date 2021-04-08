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

    public Disposable logIn(dbhelper helper, Consumer<String> onNext, Consumer<Throwable> onError) {
        isLogging.setValue(true);
        return processLogin(helper)
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

    private Observable<String> processLogin(dbhelper helper) {
        // 로그인 성공시

        db = helper.getReadableDatabase();
        String email = this.id.getValue();
        String pwd = this.password.getValue();

        // 저장된 회원정보 존부 확인
        return Observable.create(emitter -> {
            String sql = "select * from user where email = '"+email+"' and password= '"+pwd+"'";
            Cursor cursor = db.rawQuery(sql, null);
            while(cursor.moveToNext()){
                String e = cursor.getString(0);
                String p = cursor.getString(1);
                Log.d("select","email :"+e+" pwd: "+p);
            }

            if(cursor.getCount()==1){
                emitter.onNext(email+"님 환영합니다.");
            }
            else{
                emitter.onError(new Throwable("로그인 정보가 틀렸습니다."));
            }
            cursor.close();
            db.close();
            emitter.onComplete();
        });
    }
}
