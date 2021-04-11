package com.example.tosmanager.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.tosmanager.ui.RecoverPasswordActivity;
import com.example.tosmanager.ui.RecoverRequest;

import org.json.JSONObject;

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

    public Observable<String> recoverPassword(Context context) {
        isRequesting.setValue(true);

        Observable<String> observable = Observable.create(emitter -> {
            String Email = email.getValue();

            Response.Listener<String> responseListener = response -> {
                try
                {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        //회원정보 존재
                        emitter.onNext("해당 email을 확인하세요");
                    }
                    else {
                        //로그인 실패
                        emitter.onError(new Throwable("일치하는 회원정보가 없습니다."));
                    }

                } catch(Exception e)
                {
                    emitter.onError(e);
                }
            };
            RecoverRequest recoverRequest = new RecoverRequest(Email,responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(recoverRequest);
        });

        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> isRequesting.postValue(false));
    }
}
