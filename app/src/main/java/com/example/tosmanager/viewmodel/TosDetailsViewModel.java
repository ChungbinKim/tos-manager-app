package com.example.tosmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tosmanager.model.ListContents;
import com.example.tosmanager.model.TableContent;
import com.example.tosmanager.model.TermsSummary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TosDetailsViewModel extends ViewModel {
    private final MutableLiveData<CharSequence> serviceName = new MutableLiveData<>();

    public MutableLiveData<CharSequence> getServiceName() {
        return serviceName;
    }

    public Single<TermsSummary> fetchTermsSummary(String id) {
        // TODO DB에서 가져온 후 TermsSummary로 변환
        TermsSummary dummySummary = new TermsSummary(id, "휴면정책");

        ListContents lists = dummySummary.getListContents();

        lists.addContent("사업자 권리")
                .addItem("이 서비스는...")
                .addItem("이 서비스는...")
                .addItem("그리고 이 서비스는...");

        lists.addContent("사업자 의무")
                .addItem("이 서비스는...")
                .addItem("그리고 이 서비스는");

        dummySummary.getTableContent()
                .addRow("휴면 기간", "9개월")
                .addRow("장기 휴면시 계정 삭제", false);

        return Single.just(dummySummary)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteTermsSummary(String id) {
        // TODO DB에서 삭제 처리
        return Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable renameTermsSummary(String id) {
        // TODO DB에서 이름변경 처리
        return Completable.complete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
