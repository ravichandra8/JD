package com.example.user.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 28-11-2017.
 */

public class ParentResponse {

    @SerializedName("nav")
    private List<ParentCatData> navparent;




    public List<ParentCatData> getNavparent() {
        return navparent;
    }

    public void setNavparent(List<ParentCatData> navparent) {
        this.navparent = navparent;
    }


}
