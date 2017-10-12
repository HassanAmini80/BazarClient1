package com.example.hassan.bazarclient.Network;

import com.example.hassan.bazarclient.models.AuthenticationResponseModel;
import com.example.hassan.bazarclient.models.CustomerModel;
import com.example.hassan.bazarclient.models.GoodModel;
import com.example.hassan.bazarclient.models.OrderModel;
import com.example.hassan.bazarclient.models.SignInRequestMdel;

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

public interface FakeGoodService {
    @GET("Goods/{getById}")
    Call<GoodModel> getGoodById (@Path("getById") String id);

    @GET("Goods/getAll")
    Call<List<GoodModel>> GetGoods();

    @POST("order/addOrder")
    Call<String> order(@Body OrderModel orderModel);

    @POST
    Call<AuthenticationResponseModel> signIn(@Body SignInRequestMdel signInRequestMdel);

    @Multipart
    @POST("user/profile/image")
    Call<CustomerModel> uploadUserProfileImage(@Header("Authorization") String authHeader, @PartMap Map<String, RequestBody> map);

}