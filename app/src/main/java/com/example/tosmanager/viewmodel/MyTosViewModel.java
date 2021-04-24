package com.example.tosmanager.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tosmanager.R;
import com.example.tosmanager.model.DataHolder;
import com.example.tosmanager.model.TermsSummary;
import com.example.tosmanager.ui.CalendarFragment;
import com.example.tosmanager.ui.ConfigurationFragment;
import com.example.tosmanager.ui.MyTosFragment;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyTosViewModel extends ViewModel {
    public Observable<CharSequence> fetchServiceNames() {
        // TODO DB에서 가져오기
        return Observable.just("서비스 A", "서비스 B", "서비스 C", "서비스 D")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> s);
    }
}
