package com.example.dashbtest.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CalendarUtils
{
    public static LocalDate selectedDate = LocalDate.now();  // Teď už nikdy nebude null


    public static String formattedDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedTime(LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    public static void setSelectedDate(LocalDate date) {
        if (date != null) {
            selectedDate = date;
        } else {
            selectedDate = LocalDate.now();
        }
    }





}