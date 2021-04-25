package com.example.tosmanager.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.tosmanager.model.DataHolder;
import com.example.tosmanager.model.LoginSession;
import com.example.tosmanager.ui.LoginRequest;

import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private static final String TAG = LoginViewModel.class.getName();
    // UI
    private final MutableLiveData<String> id = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    // state
    private final MutableLiveData<Boolean> isLogging = new MutableLiveData<>(false);
    private final MutableLiveData<String> inputText = new MutableLiveData<>();

    public MutableLiveData<String> getID() {
        return id;
    }
    public MutableLiveData<String> getPassword() {
        return password;
    }
    public MutableLiveData<Boolean> getIsLogging() {
        return isLogging;
    }
    public MutableLiveData<String> getInputText() {
        return inputText;
    }

    public Observable<String> logIn(Context context) {
        isLogging.setValue(true);

        String Email = id.getValue();
        String Password = password.getValue();

        Observable<String> observable = Observable.create(emitter -> {
            // 로그인 성공시
            Response.Listener<String> responseListener = response -> {
                try
                {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        //로그인 성공
                        emitter.onNext(jsonResponse.getString("Email"));
                        emitter.onNext(jsonResponse.getString("Password"));
                        emitter.onComplete();
                    }
                    else {
                        //로그인 실패
                        emitter.onError(new Throwable("로그인에 실패하였습니다"));
                    }

                } catch(Exception e)
                {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            };
            LoginRequest loginRequest = new LoginRequest(Email,Password,responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(loginRequest);
        });
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> DataHolder.getInstance().setLoginSession(new LoginSession(s)))
                .doFinally(() -> isLogging.postValue(false));
    }

    public void skipLogIn() {
        DataHolder.getInstance().setLoginSession(null);
    }
}
