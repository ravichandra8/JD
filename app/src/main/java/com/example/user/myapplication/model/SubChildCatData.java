package com.example.user.myapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 28-11-2017.
 */

public class SubChildCatData {
    @SerializedName("navigationName")
    private String navnamesubchild;

    public String getNavnamesubchild() {
        return navnamesubchild;
    }

    public void setNavnamesubchild(String navnamesubchild) {
        this.navnamesubchild = navnamesubchild;
    }
}
