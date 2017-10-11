package com.example.hassan.bazarclient.Network;

import com.example.hassan.bazarclient.utility.ClientConfigs;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hassan on 7/2/2017.
 */

public class FakeGoodProvider {
    private FakeGoodService mGService;
    private Retrofit mRetrofitClient;

    public FakeGoodProvider(){
        OkHttpClient httpClient = new OkHttpClient();

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(ClientConfigs.REST_API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGService = mRetrofitClient.create(FakeGoodService.class);
    }

    public FakeGoodService getGService(){return mGService;}

    public Retrofit getRetrofitClient(){
        return mRetrofitClient;
    }
}
