package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewsActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    ListView newsList;
    String category, date, newsItemURL, author, title, description, descriptionFull, sortOption;
    ProgressBar progressBar;
    ArrayList<NewsItem> newsItemList = new ArrayList<>();
    NewsAdapter adapter;
    ArrayList<String> listURLs = new ArrayList<>();
    ImageView ttsIcon, errorIV;
    TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsList = findViewById(R.id.newsList);
        progressBar = findViewById(R.id.progressBar);
        sortOption = "published_desc";
        errorIV = findViewById(R.id.errorIV);
        ttsIcon = findViewById(R.id.ttsIcon);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            category = b.getString("newsCategory");
        }
        if (category.equals("general"))
            Objects.requireNonNull(getSupportActionBar()).setTitle("Latest News");
        else  Objects.requireNonNull(getSupportActionBar()).setTitle("Trending in: " + category);


        String url =
                "http://api.mediastack.com/v1/news?access_key=d94db2d2003c3758ef955c4487668e4c&sort=" + sortOption + "&symbols=AAPL&&languages=en&keywords=" + category;

//    Guardian   String url =  "https://content.guardianapis.com/search?q="+category+"&category="+category+"&api-key=9fac84b3-1e4a-42ef-a111-a332d4bdb392";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(url, null, this, this);
        queue.add(request);
        Log.i("xxx", request + "");


    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        errorIV.setImageResource(R.drawable.error);
        errorIV.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResponse(JSONObject response) {

        try {
//     The guardian       JSONArray articles = response.getJSONObject("response").getJSONArray("results");

            JSONArray articles = response.getJSONArray("data");

            if (articles.length() == 0) {
                progressBar.setVisibility(View.GONE);
                errorIV.setImageResource(R.drawable.error);
                errorIV.setVisibility(View.VISIBLE);

            } else {

                Log.i("xxx10", articles.toString());
                for (int i = 0; i < articles.length(); i++) {

                    author = articles.getJSONObject(i).getString("source");
                    Log.i("xxx2", author);

                    title = articles.getJSONObject(i).getString("title");
                    Log.i("xxx3", title);

                    date = articles.getJSONObject(i).getString("published_at");
                    Log.i("xxx4", date);

                    descriptionFull = articles.getJSONObject(i).getString("description");


                    if (descriptionFull.length() < 100) description = descriptionFull;
                    else description = descriptionFull.substring(0, 100);

                    newsItemList.add(new NewsItem(title, author.toUpperCase(), date.substring(0, 10), description + ".... Click here to read the full story"));
                    Log.i("xxx22", newsItemList.toString());

                    newsItemURL = articles.getJSONObject(i).getString("url");
                    listURLs.add(newsItemURL);


                }
                Log.i("xxx5", newsItemList.get(0).toString());
                Log.i("xxx5", listURLs.get(0));
            }
            List<NewsItem> newsListStream = newsItemList.parallelStream().distinct().collect(Collectors.toList());


            adapter = new NewsAdapter(this, newsListStream, listURLs);

            newsList.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);


        } catch (JSONException e) {
            errorIV.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }


    }



}

