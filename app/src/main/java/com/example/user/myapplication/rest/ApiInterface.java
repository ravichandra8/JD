package com.example.user.myapplication.rest;




import com.example.user.myapplication.Constants;
import com.example.user.myapplication.model.ParentResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("nav?api_key="+ Constants.key)
    Call<ParentResponse> getResult();


}
