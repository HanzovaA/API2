package com.example.dashbtest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.DatePickerDialog;
import java.util.Calendar;
import android.widget.DatePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;


public class starChartArea extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText latitudeInput, longitudeInput, dateInput;
    private ImageView starChartImage;
    private Button datePickerButton;
    private String API_URL = "https://api.astronomyapi.com/api/v2/studio/star-chart";
    private String API_ID = "7d4aa920-e45e-4f28-8fc4-e6e738552f46"; // Sem dej svůj API ID
    private String API_SECRET = "faa4410a8c5c7e5543a28d3b7455ef8c074639db69c8a1a944c723637d019736496425edeca76a5a258d42bdb191048b717800d0951cad0d8e2f4600893c516e5510521a69f417bbf5f683f86b090350e717e9531a5aba2e9ba92d8c5d297ead6c01d953814ad66b8c0d6ad6c4642eda"; // Sem dej svůj API SECRET

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chart_area);

        latitudeInput = findViewById(R.id.latitudeInput);
        longitudeInput = findViewById(R.id.longitudeInput);
        dateInput = findViewById(R.id.dateInput);
        Button generateButton = findViewById(R.id.generateButton);
        starChartImage = findViewById(R.id.starChartImage);
        datePickerButton = findViewById(R.id.datePickerButton);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateStarChart();
            }
        });
    }






    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastKnownLocation();
        }
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitudeInput.setText(String.valueOf(location.getLatitude()));
                    longitudeInput.setText(String.valueOf(location.getLongitude()));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            }
        }
    }








    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                starChartArea.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        dateInput.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }




    private void generateStarChart() {
        String latitude = latitudeInput.getText().toString();
        String longitude = longitudeInput.getText().toString();
        String date = dateInput.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);

        String jsonBody = "{" +
                "\"style\": \"default\"," +
                "\"observer\": {" +
                "\"latitude\": " + latitude + "," +
                "\"longitude\": " + longitude + "," +
                "\"date\": \"" + date + "\"" +
                "}," +
                "\"view\": {" +
                "\"type\": \"area\"," +
                "\"parameters\": {" +
                "\"zoom\": 3," +
                "\"position\": {" +
                "\"equatorial\": {" +
                "\"rightAscension\": 10.0," +
                "\"declination\": 20.0" +
                "}" +
                "}" +
                "}" +
                "}" +
                "}";

        try {
            JSONObject requestBody = new JSONObject(jsonBody);
            Log.d("DEBUG_JSON", requestBody.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String imageUrl = response.getJSONObject("data").getString("imageUrl");
                                Picasso.get().load(imageUrl).into(starChartImage);
                            } catch (Exception e) {
                                Log.e("API_RESPONSE_ERROR", "Chyba při parsování odpovědi", e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null) {
                                String responseBody = new String(error.networkResponse.data);
                                Log.e("API_ERROR", "Chyba API: " + responseBody);
                            } else {
                                Log.e("API_ERROR", "Neznámá chyba API", error);
                            }
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + android.util.Base64.encodeToString(
                            (API_ID + ":" + API_SECRET).getBytes(), android.util.Base64.NO_WRAP));
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("JSON_ERROR", "Chyba při sestavování JSON", e);
        }
    }

}
