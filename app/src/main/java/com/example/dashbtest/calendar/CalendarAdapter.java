package com.example.dashbtest.calendar;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashbtest.R;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    public final OnItemListener onItemListener;
    private final LocalDate selectedDate;  // Přidáme selectedDate
    private int selectedPosition = -1;


    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener,LocalDate selectedDate)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.selectedDate = selectedDate;  // Přiřazení selectedDate
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String day = daysOfMonth.get(position);
        holder.dayOfMonth.setText(day);

        if (!day.isEmpty()) {
            int dayInt = Integer.parseInt(day);
            LocalDate currentDate = LocalDate.now();

            // Zvýrazníme dnešní den, pokud se shoduje s aktuálním
            if (dayInt == currentDate.getDayOfMonth() &&
                    selectedDate.getMonthValue() == currentDate.getMonthValue() &&
                    selectedDate.getYear() == currentDate.getYear()) {

                holder.dayOfMonth.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
                holder.dayOfMonth.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            }
            // Zvýrazníme vybraný den (pokud není prázdný a není dnešní den)
            else if (position == selectedPosition) {
                holder.dayOfMonth.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
                holder.dayOfMonth.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            }
            // Výchozí barva pro ostatní dny
            else {
                holder.dayOfMonth.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
                holder.dayOfMonth.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            }
        }
    }



    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

    // **Metoda pro nastavení vybraného dne**
    public void setSelectedDay(int position) {
        selectedPosition = position;
        notifyDataSetChanged(); // Aktualizace RecyclerView pro překreslení stylů
    }
}