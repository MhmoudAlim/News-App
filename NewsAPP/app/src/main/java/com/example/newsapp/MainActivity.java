package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    GridView imagesGv;
    Integer[] imagesID;
    String[] newsCategory;
    EditText searchTv;
    ImageView searchIcon;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Hot Topics");
        imagesGv = findViewById(R.id.imagesGv);
        searchTv = findViewById(R.id.searchTv);
        searchIcon = findViewById(R.id.searchIcon);
        imagesID = new Integer[]{R.drawable.egypt, R.drawable.art,
                R.drawable.medicine, R.drawable.movies, R.drawable.politics,
                R.drawable.sports, R.drawable.tech, R.drawable.lifestyle,
                R.drawable.corona,  R.drawable.weather

        };

        newsCategory = new String[]{"Egypt", "Art", "Medicine", "Movies", "politics", "Sports", "Tech", "Life Style", "Covid-19","Weather" };

        YoYo.with(Techniques.Wobble)
                .duration(2000)
                .repeat(0)
                .playOn(findViewById(R.id.imagesGv));

        imagesGv.setAdapter(new ImageAdapterGridView(this));
        imagesGv.setOnItemClickListener((parent, view, position, id) -> {
            intent = new Intent(MainActivity.this, NewsActivity.class);
            String category = newsCategory[position];
            intent.putExtra("newsCategory", category);
            startActivity(intent);
        });
    }

    public void nightModeBtn(MenuItem item) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    public void lightModeBtn(MenuItem item) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }


    private class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return imagesID.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(550, 550));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                mImageView.setPadding(16, 8, 16, 8);
                mImageView.setPaddingRelative(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imagesID[position]);
            return mImageView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;

    }

    public void searchMenuItem(MenuItem item) {
        searchTv.setVisibility(View.VISIBLE);
        searchIcon.setVisibility(View.VISIBLE);



    }

    public void searchNewsIcon(View view) {

        intent = new Intent(MainActivity.this, NewsActivity.class);
        String category = searchTv.getText().toString();
        if (category.length() > 2) {
            intent.putExtra("newsCategory", category);
            startActivity(intent);
            searchTv.setText("");
            searchTv.setVisibility(View.GONE);
            searchIcon.setVisibility(View.GONE);
        }
        else
            Toast.makeText(this, "please enter a keyword", Toast.LENGTH_SHORT).show();

    }


    public void latestNews(MenuItem item) {
        intent = new Intent(MainActivity.this, NewsActivity.class);
        intent.putExtra("newsCategory", "general");
        startActivity(intent);

    }

}