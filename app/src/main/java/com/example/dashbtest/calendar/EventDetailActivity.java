package com.example.dashbtest.calendar;


import static com.example.dashbtest.calendar.CalendarUtils.selectedDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.dashbtest.MainActivity;
import com.example.dashbtest.MainActivity3;
import com.example.dashbtest.R;

import java.util.List;

public class EventDetailActivity extends AppCompatActivity {
    private TextView eventDetailTitleTV;
    private TextView eventDetailContentTV;
    private ImageView eventDetailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventDetailTitleTV = findViewById(R.id.eventDetailTitleTV);
        eventDetailContentTV = findViewById(R.id.eventDetailContentTV);
        eventDetailImageView = findViewById(R.id.eventDetailImageView);

        // Získání ID poznámky z intentu
        int eventId = getIntent().getIntExtra("event_id", -1);
        if (eventId == -1) {
            Toast.makeText(this, "Neplatné ID poznámky", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (eventId != -1) {
            // Načtení poznámky z databáze
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            Event event = dbHelper.getEventById(eventId);

            if (event != null) {
                // Zobrazení dat
                eventDetailTitleTV.setText(event.getTitle());
                eventDetailContentTV.setText(event.getContent());
                // Zobrazení fotografie, pokud je k dispozici
                if (event.getImagePath() != null && !event.getImagePath().isEmpty()) {
                    eventDetailImageView.setVisibility(View.VISIBLE);
                    Glide.with(this).load(event.getImagePath()).into(eventDetailImageView);
                }else {
                    eventDetailImageView.setVisibility(View.GONE);  // Skryj ImageView, pokud není fotka
                }

            } else {
                // Pokud poznámka nebyla nalezena, zobrazíme chybovou zprávu a ukončíme aktivitu
                Toast.makeText(this, "Poznámka nebyla nalezena", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            // Pokud ID není platné, zobrazíme chybovou zprávu a ukončíme aktivitu
            Toast.makeText(this, "Neplatné ID poznámky", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void setEventsAdapter() {
        // Načtení poznámek z databáze
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Event> dailyEvents = dbHelper.getEventsForDate(selectedDate);


    }
}

