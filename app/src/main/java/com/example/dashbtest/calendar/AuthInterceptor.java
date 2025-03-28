package com.example.dashbtest.calendar;

import java.io.IOException;
import android.util.Base64;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private static final String APPLICATION_ID = "7d4aa920-e45e-4f28-8fc4-e6e738552f46";
    private static final String APPLICATION_SECRET = "faa4410a8c5c7e5543a28d3b7455ef8c074639db69c8a1a944c723637d019736496425edeca76a5a258d42bdb191048b717800d0951cad0d8e2f4600893c516e5510521a69f417bbf5f683f86b090350e717e9531a5aba2e9ba92d8c5d297ead6c01d953814ad66b8c0d6ad6c4642eda";

    @Override
    public Response intercept(Chain chain) throws IOException {
        String credentials = APPLICATION_ID + ":" + APPLICATION_SECRET;
        String authValue = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", authValue)
                .build();

        return chain.proceed(newRequest);
    }
}

