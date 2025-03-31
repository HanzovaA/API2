package com.example.dashbtest;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiClient2 apiClient2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiClient2 = new ApiClient2(this);// Volání metody getUpcomingLaunches pro získání seznamu nadcházejících startů raket
        apiClient2.getUpcomingLaunches(new ApiClient2.LaunchCallback() {
            @Override
            public void onSuccess(List<Launch> launches) {
                recyclerView.setAdapter(new LaunchAdapter(launches));
            }

            @Override
            public void onError(String error) {
                // Ošetření chyby
            }
        });
    }
}