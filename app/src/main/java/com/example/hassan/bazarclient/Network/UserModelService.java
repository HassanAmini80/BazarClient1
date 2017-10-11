package com.example.hassan.bazarclient.Network;

import com.example.hassan.bazarclient.models.AuthenticationResponseModel;
import com.example.hassan.bazarclient.models.CustomerModel;
import com.example.hassan.bazarclient.models.LoginModel;
import com.example.hassan.bazarclient.models.RefreshTokenRequestModel;
import com.example.hassan.bazarclient.models.TokenModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface UserModelService {
    @POST("login")
    Call<AuthenticationResponseModel> loginUser(@Body LoginModel loginModel);

    @GET("user/{userType}/{familyId}")
    Call<List<CustomerModel>> getUsers(@Path("userType") int userType, @Path("familyId") int familyId);

    @GET("user/{userid}")
    Call<CustomerModel> getUserById(@Path("userid") String userid);

    @PUT("user/{userid}")
    Call<CustomerModel> updateUserById(@Path("userid") int userid, @Body CustomerModel driverModel);

    @DELETE("user/{userid}")
    Call<Boolean> deleteUserById(@Path("userid") String userid);

    @POST("user/addUser")
    Call<Boolean> addNewUser(@Body CustomerModel driverModel);

    @POST("start/refreshToken")
    Call<TokenModel> getRefreshToken(@Body RefreshTokenRequestModel refreshTokenRequestModel);

    @PUT("user/profile")
    Call<CustomerModel> updateUserProfile(@Body CustomerModel driverModel);

    @Multipart
    @POST("user/profile/image")
    Call<CustomerModel> uploadUserProfileImage(@Header("Authorization") String authHeader, @PartMap Map<String, RequestBody> map);

    @DELETE("user/terminate")
    Call<Boolean> terminateApp();
}
