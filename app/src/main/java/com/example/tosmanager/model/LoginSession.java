package com.example.tosmanager.model;

// 로그인한 생태의 session 정보
public class LoginSession {
    // TODO: 인증 방식에 따라 더 정확하게 정의
    private String token;

    public LoginSession(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
