

package com.example.dashbtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private static final String API_KEY = "WS8Ap9HLe7oTKUgUqWSvU7TE1wSAUELxMZdFGZ9b";
    private ImageView apodImageView;
    private TextView titleTextView, explanationTextView, dateTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        apodImageView = findViewById(R.id.apodImageView);
        titleTextView = findViewById(R.id.titleTextView);
        explanationTextView = findViewById(R.id.explanationTextView);
        dateTextView = findViewById(R.id.dateTextView);

        fetchApod();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void fetchApod() {
        NasaApiService apiService = ApiClient.getClient().create(NasaApiService.class);
        Call<ApodResponse> call = apiService.getApod(API_KEY);

        call.enqueue(new Callback<ApodResponse>() {
            @Override
            public void onResponse(Call<ApodResponse> call, Response<ApodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApodResponse apod = response.body();
                    titleTextView.setText(apod.title);
                    explanationTextView.setText(apod.explanation);
                    dateTextView.setText("Date: " + apod.date);
                    Glide.with(MainActivity2.this).load(apod.imageUrl).into(apodImageView);
                }
            }

            @Override
            public void onFailure(Call<ApodResponse> call, Throwable t) {
                Log.e("API_ERROR", "Chyba při načítání dat: " + t.getMessage());
            }
        });
    }








}