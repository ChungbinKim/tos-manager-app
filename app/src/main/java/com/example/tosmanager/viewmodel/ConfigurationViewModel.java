package com.example.tosmanager.viewmodel;

import androidx.lifecycle.ViewModel;

import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConfigurationViewModel extends ViewModel {
    public Completable importData(InputStream data) {
        // TODO: 입력 -> DB에 저장
        return Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable exportData(OutputStream output) {
        // TODO: DB 가져오기 -> 출력
        return Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
