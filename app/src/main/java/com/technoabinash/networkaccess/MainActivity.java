package com.technoabinash.networkaccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<NewsHeadlineModel> headlineModels;
    NewsAdapter adapter;
    private static final String API_KEy = "c24dcfe4f23e4eefa8998ce9c557244b";
    private static final String URL_DATA = "http://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.network_sample);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;
            Button button = dialog.findViewById(R.id.button);
            button.setOnClickListener(view -> {
                recreate();
            });
            dialog.show();
        } else {
            // if network avilable
            recyclerView = findViewById(R.id.newsRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            headlineModels = new ArrayList<>();

            StringRequest request = new StringRequest(Request.Method.GET, URL_DATA + API_KEy, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray array = object.getJSONArray("articles");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("source");

                            String title = jsonObject.getString("title");
                            String content = jsonObject.getString("description");
                            String imageUrl = jsonObject.getString("urlToImage");
                            String source = jsonObject1.getString("name");
                            NewsHeadlineModel model = new NewsHeadlineModel(title, content, imageUrl, source);
                            headlineModels.add(model);

                        }
                        adapter = new NewsAdapter(headlineModels, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(request);


        }
    }


}