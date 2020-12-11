package com.example.fetchproductassignment.models;

public class RegistrationModel {
    private String email;
    private String firstName;
    private String password;
    private String mobile;

    public RegistrationModel(String email, String firstName, String password, String mobile) {
        this.email = email;
        this.firstName = firstName;
        this.password = password;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
