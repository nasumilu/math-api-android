package com.nasumilu.calculator.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CalculatorService {

    @Headers({"Content-Type: application/xml; charset=utf-8"})
    @POST("math")
    public Call<String> calculate(@Body String xml);

}
