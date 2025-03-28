package com.example.dashbtest.calendar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AstronomyApiService {
    @GET("studio/star-chart")
    Call<StarChartResponse> getStarChart(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("style") String style,
            @Query("constellations") boolean constellations
    );
}

