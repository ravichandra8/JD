package com.example.user.myapplication.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    //for get service will work
//    public static final String BASE_URL = "http://220.225.38.123:8081/LogicShore.svc/";

    public static final String BASE_URL = "https://uat.jdgroupmeshtest.cloud/stores/jdsports/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
