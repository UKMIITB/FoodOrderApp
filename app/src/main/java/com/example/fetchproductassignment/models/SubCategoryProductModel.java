package com.example.fetchproductassignment.models;

import java.io.Serializable;

public class SubCategoryProductModel implements Serializable {
    private int quantity;
    private String description;
    private String _id;
    private String productName;
    private String image;
    private String unit;
    private double price;
    private double mrp;

    public SubCategoryProductModel(int quantity, String description, String _id,
                                   String productName, String image, String unit,
                                   double price, double mrp) {
        this.quantity = quantity;
        this.description = description;
        this._id = _id;
        this.productName = productName;
        this.image = image;
        this.unit = unit;
        this.price = price;
        this.mrp = mrp;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String get_id() {
        return _id;
    }

    public String getProductName() {
        return productName;
    }

    public String getImage() {
        return image;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public double getMrp() {
        return mrp;
    }
}
