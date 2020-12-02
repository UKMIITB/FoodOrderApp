package com.example.fetchproductassignment.models;

public class AllCategoryModel {
    private String catName;
    private String catImage;
    private int catId;

    public AllCategoryModel(String catName, String catImage, int catId) {
        this.catName = catName;
        this.catImage = catImage;
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }
}
