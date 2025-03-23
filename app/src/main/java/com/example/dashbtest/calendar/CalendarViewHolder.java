package com.example.dashbtest.calendar;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashbtest.R;

import java.time.LocalDate;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dayOfMonth;
    private final CalendarAdapter adapter;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter adapter) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.adapter = adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        String dayText = (String) dayOfMonth.getText();
        if (!dayText.isEmpty()) {
            adapter.setSelectedDay(position);  // Nastavíme vybraný den
            adapter.onItemListener.onItemClick(position, dayText);  // Předáme informaci do MainActivity
        }
    }

    // Metoda pro nastavení barvy pro aktuální den
    public void setCurrentDayStyle(LocalDate selectedDate, int dayInt) {
        // Získáme aktuální den
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Zkontrolujeme, jestli je daný den aktuální
        if (dayInt == currentDay && selectedDate.getMonthValue() == currentMonth && selectedDate.getYear() == currentYear) {
            // Zvýraznění aktuálního dne
            dayOfMonth.setBackgroundColor(ContextCompat.getColor(dayOfMonth.getContext(), R.color.colorAccent)); // Použij vlastní barvu nebo systémovou
            dayOfMonth.setTextColor(ContextCompat.getColor(dayOfMonth.getContext(), R.color.white)); // Text bude bílý
        } else {
            // Obnovíme výchozí barvu
            dayOfMonth.setBackgroundColor(ContextCompat.getColor(dayOfMonth.getContext(), android.R.color.transparent));
            dayOfMonth.setTextColor(ContextCompat.getColor(dayOfMonth.getContext(), R.color.black));
        }
    }
}
