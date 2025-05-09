package com.example.dashbtest.calendar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import com.example.dashbtest.R;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventTitleET;
    private EditText eventContentET;
    private TextView eventDateTV;
    private TextView eventTimeTV;
    private LocalTime time;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String imagePath;
    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;  // Identifikátor pro pořízení fotky kamerou

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initWidgets();

        // Získání data z intentu
        String date = getIntent().getStringExtra("selectedDate");
        if (date == null) {
            // Pokud datum není předáno, ukončíme aktivitu
            finish();
            return;
        }

        CalendarUtils.selectedDate = LocalDate.parse(date);
        time = LocalTime.now();

        // Nastavení data a času
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));

        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
                checkPermissions();  // Kontrola oprávnění před otevřením výběru fotografie
            }
        });


    }
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ potřebuje READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_CODE_PERMISSIONS
                );
            } else {
                openImagePicker();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0-12 potřebuje READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS
                );
            } else {
                openImagePicker();
            }
        } else {
            // Android <6.0 nepotřebuje runtime oprávnění
            openImagePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Oprávnění udělena, můžeš pokračovat
                openImagePicker();
            } else {
                // Oprávnění zamítnuta, informuj uživatele
                Toast.makeText(this, "Oprávnění jsou nutná pro výběr fotografie", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImagePicker() {
        // Moderní způsob (funguje na všech verzích)
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            // Získání trvalého přístupu k souboru (důležité pro Android 10+)
            getContentResolver().takePersistableUriPermission(
                    imageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            imagePath = imageUri.toString();
            // Nyní máte URI fotky uložené v imagePath
        }
    }
    private void initWidgets() {
        eventTitleET = findViewById(R.id.eventTitleET);
        eventContentET = findViewById(R.id.eventContentET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        // Kontrola, zda komponenty nejsou null
        if (eventTitleET == null || eventContentET== null  || eventDateTV== null  || eventTimeTV == null ) {
            throw new IllegalStateException("Některá komponenta nebyla nalezena v layoutu.");
        }
    }

    public void saveEventAction(View view) {
        String title = eventTitleET.getText().toString().trim();
        String content = eventContentET.getText().toString().trim();
       // String imagePath = "";  // Cesta k fotografii (může být prázdná, pokud uživatel nevybere fotku)
        // Kontrola prázdných polí
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Vyplňte všechna pole", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vytvoření nové události (bez ID)
        Event newEvent = new Event(title, content, CalendarUtils.selectedDate, time, imagePath);

        // Uložení do databáze
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long result = dbHelper.addEvent(newEvent);

        // Zobrazení výsledku ukládání
        if (result == -1) {
            Toast.makeText(this, "Chyba při ukládání události", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Událost uložena", Toast.LENGTH_SHORT).show();
        }

        // Ukončení aktivity
        finish();
    }




}