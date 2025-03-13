package com.example.testapi3;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiClient apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiClient = new ApiClient(this);
        apiClient.getUpcomingLaunches(new ApiClient.LaunchCallback() {
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
