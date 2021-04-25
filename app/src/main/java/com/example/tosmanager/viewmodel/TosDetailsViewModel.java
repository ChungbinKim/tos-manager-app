package com.example.tosmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tosmanager.model.TermsSummary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TosDetailsViewModel extends ViewModel {
    private final MutableLiveData<CharSequence> serviceName = new MutableLiveData<>();

    public MutableLiveData<CharSequence> getServiceName() {
        return serviceName;
    }

    public Single<TermsSummary> fetchTermsSummary(String id) {
        // TODO DB에서 가져온 후 TermsSummary로 변환
        return Single.just(new TermsSummary(id, "휴면정책"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
