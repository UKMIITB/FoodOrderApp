package com.example.fetchproductassignment.models;

public class SubCategoryModel {
    private int subId;
    private String subName;

    public SubCategoryModel(int subId, String subName) {
        this.subId = subId;
        this.subName = subName;
    }

    public int getSubId() {
        return subId;
    }

    public String getSubName() {
        return subName;
    }
}
