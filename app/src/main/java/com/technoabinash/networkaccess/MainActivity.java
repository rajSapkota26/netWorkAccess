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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.technoabinash.networkaccess.apicall.ApiClient;
import com.technoabinash.networkaccess.apicall.apiInterface;
import com.technoabinash.networkaccess.entities.Articles;
import com.technoabinash.networkaccess.entities.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<NewsHeadlineModel> headlineModels;
    NewsAdapter adapter;
    List<Articles> articles = new ArrayList<>();
    private static final String API_KEy = "c24dcfe4f23e4eefa8998ce9c557244b";
    private static final String URL_DATA = "https://newsapi.org/v2/top-headlines?country=us&apiKey=";


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
            loadNews();


        }
    }

    public void loadNews() {
        apiInterface apiInterface = ApiClient.getRetrofit().create(apiInterface.class);
        Call<News> call;
        call = apiInterface.getNews("us", API_KEy);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    News news = response.body();
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    for (Articles articles:articles){
                        String source=articles.getSource().getName();
                        String title = articles.getTitle();
                        String content = articles.getDescription();
                        String imageUrl = articles.getUrlToImage();
                        NewsHeadlineModel model = new NewsHeadlineModel(title, content, imageUrl, source);
                        headlineModels.add(model);
                    }
                    adapter = new NewsAdapter(headlineModels, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, "no list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });

    }


}


/*RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JSONObject obj=new JSONObject();
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
                        adapter = new NewsAdapter(headlineModels, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);*/