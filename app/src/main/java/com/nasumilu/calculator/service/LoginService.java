package com.nasumilu.calculator.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LoginService {

    @FormUrlEncoded
    @POST("oauth/token")
    public Call<String> token(@Field("grant_type") String grantType,
                              @Field("client_secret") String clientSecret,
                              @Field("client_id") String clientId,
                              @Field("username") String userName,
                              @Field("password") String password);

}
