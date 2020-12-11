package com.example.fetchproductassignment.app;

public class EndPoints {
    private static final String URL_CATEGORY = "category";
    private static final String URL_SUB_CATEGORY = "subcategory/";
    private static final String URL_SUB_CATEGORY_PRODUCTS = "products/sub/";
    private static final String URL_REGISTER = "auth/register";
    private static final String URL_LOGIN = "auth/login";

    public static String getCategory() {
        return Config.BASE_URL + URL_CATEGORY;
    }

    public static String getSubCategory(int catId) {
        return Config.BASE_URL + URL_SUB_CATEGORY + catId;
    }

    public static String getSubCategoryProducts(int subId) {
        return Config.BASE_URL + URL_SUB_CATEGORY_PRODUCTS + subId;
    }

    public static String getRegistration() {
        return Config.BASE_URL + URL_REGISTER;
    }

    public static String getLogin() {
        return Config.BASE_URL + URL_LOGIN;
    }
}
