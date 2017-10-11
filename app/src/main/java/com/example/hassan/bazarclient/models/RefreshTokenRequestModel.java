package com.example.hassan.bazarclient.models;


import com.example.hassan.bazarclient.utility.ClientConfigs;

public class RefreshTokenRequestModel {
    public String client_id;
    public String client_key;
    public String refresh_token;

    public RefreshTokenRequestModel() {
        this.client_id = ClientConfigs.CLIENT_ID;
        this.client_key = ClientConfigs.CLIENT_KEY;
    }
}
