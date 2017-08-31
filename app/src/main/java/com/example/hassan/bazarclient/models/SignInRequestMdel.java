package com.example.hassan.bazarclient.models;

import com.example.hassan.bazarclient.utility.ClientConfigs;

/**
 * Created by Hassan on 7/3/2017.
 */

public class SignInRequestMdel {
    public String client_id;
    public String client_key;

    public SignInRequestMdel(){
        this.client_id = ClientConfigs.CLIENT_ID;
        this.client_id = ClientConfigs.CLIENT_KEY;
    }

}
