package com.example.dashbtest;
import com.google.gson.annotations.SerializedName;

public class ApodResponse {
    @SerializedName("title")
    public String title;

    @SerializedName("url")
    public String imageUrl;

    @SerializedName("explanation")
    public String explanation;

    @SerializedName("date")
    public String date;
}

