package com.example.dashbtest.calendar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.dashbtest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class starChart extends AppCompatActivity {

    private WebView starChartWebView;

    private static final int LOCATION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chart);

        starChartWebView = findViewById(R.id.starChartWebView);
        starChartWebView.setWebViewClient(new WebViewClient());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getUserLocation(); // Načte polohu uživatele
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    getStarChart(latitude, longitude); // Pošli souřadnice do API
                } else {
                    Toast.makeText(starChart.this, "Nepodařilo se získat polohu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getStarChart(double latitude, double longitude) {
        AstronomyApiService apiService = RetrofitClient.getClient().create(AstronomyApiService.class);
        Call<StarChartResponse> call = apiService.getStarChart(latitude, longitude, "default", true);

        call.enqueue(new Callback<StarChartResponse>() {
            @Override
            public void onResponse(Call<StarChartResponse> call, Response<StarChartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String imageUrl = response.body().getData();
                    starChartWebView.loadUrl(imageUrl);
                } else {
                    Toast.makeText(starChart.this, "Chyba při načítání mapy", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StarChartResponse> call, Throwable t) {
                Toast.makeText(starChart.this, "Chyba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Oprávnění k lokaci
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Toast.makeText(this, "Přístup k lokaci zamítnut", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
