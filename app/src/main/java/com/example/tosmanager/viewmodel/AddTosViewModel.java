package com.example.tosmanager.viewmodel;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddTosViewModel extends ViewModel {
    public Completable summarize(CharSequence input) {
        // TODO 요약 -> 결과 DB 저장
        return Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
