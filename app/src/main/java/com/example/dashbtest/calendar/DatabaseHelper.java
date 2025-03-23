package com.example.dashbtest.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calendar.db";
    private static final int DATABASE_VERSION = 5;

    // Název tabulky a sloupce
    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";  // Název poznámky
    private static final String COLUMN_CONTENT = "content";  // Obsah poznámky
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_IMAGE_PATH = "imagePath";
    // SQL příkaz pro vytvoření tabulky
    private static final String CREATE_TABLE_EVENTS =
            "CREATE TABLE " + TABLE_EVENTS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_CONTENT + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_TIME + " TEXT,"
                    + COLUMN_IMAGE_PATH + " TEXT"  // Nový sloupec pro cestu k fotografii
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Vytvoření tabulky při prvním spuštění
        db.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Pokud změníš schéma databáze, můžeš zde provést potřebné změny
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);*/
        if (oldVersion < 2) {  // Verze 2 přidává sloupec pro fotografii
            db.execSQL("ALTER TABLE " + TABLE_EVENTS + " ADD COLUMN " + COLUMN_IMAGE_PATH + " TEXT");
        }
    }

    // Metoda pro přidání události
    public long addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_CONTENT, event.getContent());
        values.put(COLUMN_DATE, event.getDate().toString());
        values.put(COLUMN_TIME, event.getTime().toString());
        values.put(COLUMN_IMAGE_PATH, event.getImagePath());  // Uložení cesty k fotografii
        // Vložení události do databáze a vrácení ID
        long result = db.insert(TABLE_EVENTS, null, values);
        db.close();
        return result;
    }

    // Metoda pro získání událostí pro konkrétní datum
    public List<Event> getEventsForDate(LocalDate date) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_DATE, COLUMN_TIME, COLUMN_IMAGE_PATH};
        String selection = COLUMN_DATE + " = ?";
        String[] selectionArgs = {date.toString()};

        Cursor cursor = db.query(TABLE_EVENTS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                LocalDate eventDate = LocalDate.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                LocalTime eventTime = LocalTime.parse(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH));  // Načtení cesty k fotografii
                events.add(new Event(id,title, content, eventDate, eventTime, imagePath));
            } while (cursor.moveToNext());
        } else {
            Log.e("DatabaseHelper", "Cursor je prázdný nebo neinicializovaný");
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return events;
    }

    // Metoda pro získání události podle ID
    public Event getEventById(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_DATE, COLUMN_TIME, COLUMN_IMAGE_PATH};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(eventId)};

        Cursor cursor = db.query(TABLE_EVENTS, columns, selection, selectionArgs, null, null, null);

        Event event = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
            LocalDate eventDate = LocalDate.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            LocalTime eventTime = LocalTime.parse(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
            String imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH));  // Načtení cesty k fotografii
            event = new Event(id, title, content, eventDate, eventTime, imagePath);  // Použití konstruktoru s 5 argumenty
        }
        cursor.close();
        db.close();
        return event;
    }
}