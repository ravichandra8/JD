package com.example.user.myapplication.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 28-11-2017.
 */

public class ParentCatData {

    @SerializedName("navigationName")
    private String navName;


    @SerializedName("children")
    private List<childCatData> childcatarray;



    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }

    public List<childCatData> getChildcatarray() {
        return childcatarray;
    }

    public void setChildcatarray(List<childCatData> childcatarray) {
        this.childcatarray = childcatarray;
    }
}
