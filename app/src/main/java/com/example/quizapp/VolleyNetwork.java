package com.example.quizapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyNetwork {

    private static VolleyNetwork instance;
    private RequestQueue requestQueue;
    private Context context;

    public VolleyNetwork(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();

    }

    public static VolleyNetwork getInstance(Context context){
        if(instance == null){
            instance = new VolleyNetwork(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void addToRequestQueue(Request request){
        getRequestQueue().add(request);
    }
}
