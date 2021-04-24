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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyTosViewModel extends ViewModel {
    private MutableLiveData<Integer> sortID = new MutableLiveData<>(R.id.sortByRecency);
    private ArrayList<CharSequence> serviceNames = new ArrayList<>();

    public MutableLiveData<Integer> getSortID() {
        return sortID;
    }
    public ArrayList<CharSequence> getServiceNames() {
        return serviceNames;
    }

    public Observable<CharSequence> fetchServiceNames() {
        serviceNames.clear();
        // TODO DB에서 가져오기
        return Observable.just("서비스 A", "서비스 B", "서비스 C", "서비스 D")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> serviceNames.add(s))
                .map(s -> s);
    }

    public void sortServices() {
        Comparator<CharSequence> comparator;
        switch (sortID.getValue()) {
            case R.id.sortByName:
                comparator = (a, b) -> a.toString().compareToIgnoreCase(b.toString());
                break;
            case R.id.sortByNameInReverse:
                comparator = (a, b) -> -a.toString().compareToIgnoreCase(b.toString());
                break;
            default:
                // TODO recency 구현
                comparator = (a, b) -> -1;
                break;
        }
        Collections.sort(serviceNames, comparator);
    }
}
