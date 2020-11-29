package com.example.fetchproductassignment.models;

public class AllCategoryModel {
    private String imageUrl;
    private String categoryName;

    public AllCategoryModel(String imageUrl, String categoryName) {
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
