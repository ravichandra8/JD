package com.example.user.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 28-11-2017.
 */

public class childCatData {
    @SerializedName("navigationName")
    private String navNamechild;


    @SerializedName("children")
    private List<SubChildCatData> subchildcatarray;


    public String getNavNamechild() {
        return navNamechild;
    }

    public void setNavNamechild(String navNamechild) {
        this.navNamechild = navNamechild;
    }

    public List<SubChildCatData> getSubchildcatarray() {
        return subchildcatarray;
    }

    public void setSubchildcatarray(List<SubChildCatData> subchildcatarray) {
        this.subchildcatarray = subchildcatarray;
    }
}
