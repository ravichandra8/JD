package com.example.user.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lenovo on 29-11-2017.
 */

public class ErrorResponse {


    @SerializedName("message")
    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
