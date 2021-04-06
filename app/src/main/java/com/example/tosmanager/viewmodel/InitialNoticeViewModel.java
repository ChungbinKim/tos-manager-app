package com.example.tosmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InitialNoticeViewModel extends ViewModel {
    // UI
    private final MutableLiveData<String> confirmText = new MutableLiveData<>("");

    public MutableLiveData<String> getConfirmText() {
        return confirmText;
    }

    public boolean isConfirmed() {
        return confirmText.getValue().matches("(?i)^\"?(동의합니다|I Agree)\\.?\"?$");
    }
}