package com.example.dashbtest.calendar;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int id;  // ID události (pro SQLite)
    private String title;  // Název události
    private String content; // Obsah události
    private LocalDate date; // Datum události
    private LocalTime time; // Čas události
    private String imagePath;  // Nové pole pro cestu k fotografii

    // Konstruktor pro vytvoření nové události (bez ID)
    public Event(String title, String content, LocalDate date, LocalTime time, String imagePath) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.imagePath = imagePath;
    }

    // Konstruktor pro načtení události z databáze (s ID)
    public Event(int id, String title, String content, LocalDate date, LocalTime time, String imagePath) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.imagePath = imagePath;
    }

    // Gettery a settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getImagePath() { return imagePath; }  // Getter pro cestu k fotografii
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }  // Setter pro cestu k fotografii
}