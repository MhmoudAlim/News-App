package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class NewsAdapter extends ArrayAdapter<NewsItem> implements TextToSpeech.OnInitListener {
    TextView authorTv, titleTv, dateTv, discrTv;
    ImageView ttsIcon;
    TextToSpeech tts;
    ArrayList<String> listURLs;

    public NewsAdapter(@NonNull Context context, List<NewsItem> NewsItem, ArrayList<String> listURLs) {
        super(context, 0, NewsItem);
        this.listURLs = listURLs;
        tts =new TextToSpeech(getContext(), this);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_listitem, parent, false);
        }
        authorTv = convertView.findViewById(R.id.authorTv);
        titleTv = convertView.findViewById(R.id.titleTv);
        dateTv = convertView.findViewById(R.id.dateTv);
        discrTv = convertView.findViewById(R.id.discrTv);
        ttsIcon = convertView.findViewById(R.id.ttsIcon);


        authorTv.setText(getItem(position).getNewsItemAuthor());
        titleTv.setText(getItem(position).getNewsItemTitle());
        dateTv.setText(getItem(position).getNewsItemDate());
        discrTv.setText(getItem(position).getNewsDescription());

        discrTv.setOnClickListener(v -> {
            String newsURL = listURLs.get(position);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(newsURL));
            getContext().startActivity(intent);

        });

        ttsIcon.setOnClickListener(v -> tts.speak(getItem(position).getNewsItemTitle() , TextToSpeech.QUEUE_FLUSH,null,null));

        return convertView;


    }

    @Override
    public void onInit(int status) {

    }
}
