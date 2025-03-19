package com.example.dashbtest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApiService {
    @GET("planetary/apod") // říkáme Retrofit, že má volat API na této adrese.
    Call<com.example.dashbtest.ApodResponse> getApod(@Query("api_key") String apiKey); // API vyžaduje klíč jako parametr.
}
