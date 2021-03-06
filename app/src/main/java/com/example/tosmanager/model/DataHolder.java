package com.example.tosmanager.model;

import androidx.annotation.Nullable;

// Activity간 공유 가능한 main memory 수준의 data 공간
public class DataHolder {
    // TODO: session token은 여기 있으면 안 되고, 영구 disk에 저장. 아래 getter, setter 수정.
    private LoginSession loginSession = null;
    @Nullable
    public LoginSession getLoginSession() {
        return loginSession;
    }
    public void setLoginSession(@Nullable LoginSession loginSession) {
        this.loginSession = loginSession;
    }

    // Singleton
    private static final DataHolder dataHolder = new DataHolder();
    private DataHolder() {}
    public static DataHolder getInstace() {
        return dataHolder;
    }
}
