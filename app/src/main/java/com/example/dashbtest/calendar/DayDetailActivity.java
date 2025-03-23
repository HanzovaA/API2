package com.example.dashbtest.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashbtest.R;

import java.time.LocalDate;
import java.util.List;

public class DayDetailActivity extends AppCompatActivity {
    private TextView dayDateTV;
    private RecyclerView eventsRecyclerView;
    private Button addNoteButton;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);

        initWidgets();

        // Získání data z MainActivity
        selectedDate = LocalDate.parse(getIntent().getStringExtra("selectedDate"));
        dayDateTV.setText(CalendarUtils.formattedDate(selectedDate));

        // Nastavení RecyclerView pro zobrazení poznámek
        setEventsAdapter();

        // Tlačítko pro přidání nové poznámky
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayDetailActivity.this, EventEditActivity.class);
                intent.putExtra("selectedDate", selectedDate.toString());
                startActivity(intent);
            }
        });
    }

    private void initWidgets() {
        dayDateTV = findViewById(R.id.dayDateTV);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        addNoteButton = findViewById(R.id.addNoteButton);
    }

    private void setEventsAdapter() {
        // Načtení poznámek z databáze
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Event> dailyEvents = dbHelper.getEventsForDate(selectedDate);

        // Nastavení adapteru pro RecyclerView
        EventAdapter eventAdapter = new EventAdapter(dailyEvents, new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                // Otevření detailu poznámky
                Intent intent = new Intent(DayDetailActivity.this, EventDetailActivity.class);
                intent.putExtra("event_id", event.getId());
                startActivity(intent);
            }
        });
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerView.setAdapter(eventAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Aktualizace seznamu poznámek při návratu z EventEditActivity
        setEventsAdapter();
    }
}