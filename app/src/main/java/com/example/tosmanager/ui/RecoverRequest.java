package com.example.tosmanager.ui;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.tosmanager.Config;

import java.util.HashMap;
import java.util.Map;

public class RecoverRequest extends StringRequest {
    final static private String URL = String.format("%s/login.php", Config.SERVER_URL);
    private Map<String, String> parameters;

    public RecoverRequest(String Email, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email",Email);
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
