package com.example.tosmanager.util;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.MutableLiveData;

// 입력한 text를 그대로 live data에 적용시키는 event-handler
public class ForwardText implements TextWatcher {
    private MutableLiveData<String> liveData;
    public ForwardText(MutableLiveData<String> liveData) {
        this.liveData = liveData;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        liveData.setValue(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
