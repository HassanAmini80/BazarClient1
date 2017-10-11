package com.example.hassan.bazarclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Farshad on 5/12/2017.
 */

public class CustomerModel {
    private int customerId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @SerializedName("image")
    public String imageUrl;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CustomerModel(){

    }

    public CustomerModel(String username, String password) {
        this.username = username;
        this.password = password;
    }



}
