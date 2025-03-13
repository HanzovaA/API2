package com.example.testapi3;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.List;

public class ApiClient {
    private static final String API_URL = "https://lldev.thespacedevs.com/2.2.0/launch/upcoming/";
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    public ApiClient(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getUpcomingLaunches(final LaunchCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Type listType = new TypeToken<List<Launch>>() {}.getType();
                        List<Launch> launches = gson.fromJson(response.optJSONArray("results").toString(), listType);
                        callback.onSuccess(launches);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
            }
        });

        requestQueue.add(request);
    }

    public interface LaunchCallback {
        void onSuccess(List<Launch> launches);
        void onError(String error);
    }
}
