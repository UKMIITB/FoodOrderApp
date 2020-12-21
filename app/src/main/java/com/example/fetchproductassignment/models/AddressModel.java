package com.example.fetchproductassignment.models;

import java.io.Serializable;

public class AddressModel implements Serializable {
    private String userId;
    private long pincode;
    private String city;
    private String streetName;
    private String houseNo;
    private String type;

    public AddressModel(String userId, long pincode, String city, String streetName, String houseNo, String type) {
        this.userId = userId;
        this.pincode = pincode;
        this.city = city;
        this.streetName = streetName;
        this.houseNo = houseNo;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
