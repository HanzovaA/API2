package com.example.dashbtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dashbtest.calendar.MainActivity4;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);


        CardView cardView1 = findViewById(R.id.cardView1);
        cardView1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity5.this, starChartCon.class);
            startActivity(intent);
        });

        CardView cardView2 = findViewById(R.id.cardView2);
        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity5.this, starChartArea.class);
            startActivity(intent);
        });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}