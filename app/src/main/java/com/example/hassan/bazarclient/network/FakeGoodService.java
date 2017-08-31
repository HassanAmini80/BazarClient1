package com.example.hassan.bazarclient.network;

import com.example.hassan.bazarclient.models.AuthenticationResponseModel;
import com.example.hassan.bazarclient.models.GoodModel;
import com.example.hassan.bazarclient.models.SignInRequestMdel;
import com.example.hassan.bazarclient.models.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by Hassan on 6/29/2017.
 */

public interface FakeGoodService {
    @POST("Goods/{getById}")
    Call<GoodModel> getGoodById (@Path("getById") String id);

    @GET("Goods/getAll")
    Call<List<GoodModel>> GetGoods();

    @POST
    Call<AuthenticationResponseModel> signIn(@Body SignInRequestMdel signInRequestMdel);

    @Multipart
    @POST("user/profile/image")
    Call<UserModel> uploadUserProfileImage(@Header("Authorization") String authHeader, @PartMap Map<String, RequestBody> map);

}