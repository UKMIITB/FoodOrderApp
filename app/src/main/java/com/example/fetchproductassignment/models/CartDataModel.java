package com.example.fetchproductassignment.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "CartDB")
public class CartDataModel implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String productName;
    private String image;
    private double price;
    private int quantity;

    public CartDataModel(String id, String productName, String image, double price) {
        this.id = id;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.quantity = 1;
    }

    @Ignore
    public CartDataModel(String id, String productName, String image, double price, int quantity) {
        this.id = id;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {

        return Math.round(price * 100.0) / 100.0;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

