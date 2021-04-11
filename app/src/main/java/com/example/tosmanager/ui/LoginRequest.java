package com.example.tosmanager.ui;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.tosmanager.Config;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = String.format("%s/login.php", Config.SERVER_URL);
    private Map<String, String> parameters;

    public LoginRequest(String Email, String Password, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email",Email);
        parameters.put("Password",Password);
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}

