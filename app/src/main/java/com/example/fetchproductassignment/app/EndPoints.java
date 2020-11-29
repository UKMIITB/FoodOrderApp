package com.example.fetchproductassignment.app;

import com.example.fetchproductassignment.app.Config;

public class EndPoints {
    private static final String URL_CATEGORY = "category";
    private static final String URL_PRODUCT = "products";

    public static String getCategory() {
        return Config.BASE_URL + URL_CATEGORY;
    }

    public static String getAllProducts() {
        return Config.BASE_URL + URL_PRODUCT;
    }
}
