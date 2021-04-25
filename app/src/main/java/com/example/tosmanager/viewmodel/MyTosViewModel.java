package com.example.tosmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tosmanager.R;
import com.example.tosmanager.model.SearchResultItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyTosViewModel extends ViewModel {
    private final ArrayList<CharSequence> serviceNames = new ArrayList<>();

    private final MutableLiveData<Integer> sortID = new MutableLiveData<>();

    private final MutableLiveData<String> searchKeyword = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> isOnSearch = new MutableLiveData<>(false);
    private ArrayList<SearchResultItem> listItems;

    public ArrayList<CharSequence> getServiceNames() {
        return serviceNames;
    }
    public MutableLiveData<Integer> getSortID() {
        return sortID;
    }
    public MutableLiveData<String> getSearchKeyword() {
        return searchKeyword;
    }
    public MutableLiveData<Boolean> getIsOnSearch() {
        return isOnSearch;
    }
    public ArrayList<SearchResultItem> getListItems() {
        return listItems;
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

    public void sortListItems() {
        Comparator<SearchResultItem> comparator;
        switch (sortID.getValue()) {
            case R.id.sortByName:
                comparator = this::compareSearchResultItemByName;
                break;
            case R.id.sortByNameInReverse:
                comparator = (a, b) -> -compareSearchResultItemByName(a, b);
                break;
            default:
                // TODO recency 구현
                comparator = (a, b) -> -1;
                break;
        }
        Collections.sort(listItems, comparator);
    }

    public void updateListItems() {
        String[] splitKeywords = searchKeyword.getValue().split(" ");

        // Filter empty strings
        ArrayList<String> keywords = new ArrayList<>();
        for (String k : splitKeywords) {
            if (!k.isEmpty()) {
                keywords.add(k);
            }
        }

        if (keywords.isEmpty()) {
            serviceNamesToSearchResult();
            return;
        }

        listItems = new ArrayList<>();
        for (CharSequence s : serviceNames) {
            boolean containsAll = true;
            SearchResultItem.Builder builder = new SearchResultItem.Builder();

            for (String k : keywords) {
                int index = s.toString().toLowerCase().indexOf(k.toLowerCase());
                if (index == -1) {
                    containsAll = false;
                    break;
                }

                builder.addHighlightRange(index, index + k.length());
            }

            if (containsAll) {
                listItems.add(builder.serviceName(s).create());
            }
        }
    }

    private void serviceNamesToSearchResult() {
        listItems = new ArrayList<>();
        for (CharSequence s : serviceNames) {
            listItems.add(new SearchResultItem.Builder().serviceName(s).create());
        }
    }

    private int compareSearchResultItemByName(SearchResultItem a, SearchResultItem b) {
        return a.getServiceName().toString().compareToIgnoreCase(b.getServiceName().toString());
    }
}
