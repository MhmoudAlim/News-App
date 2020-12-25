package com.example.newsapp;

import java.util.Objects;

public class NewsItem {

    String newsItemTitle, newsItemAuthor, newsItemDate, newsDescription;

    public NewsItem(String newsItemTitle, String newsItemAuthor, String newsItemDate, String newsDescription) {
        this.newsItemTitle = newsItemTitle;
        this.newsItemAuthor = newsItemAuthor;
        this.newsItemDate = newsItemDate;
        this.newsDescription = newsDescription;
    }


    public String getNewsItemTitle() {
        return newsItemTitle;
    }

    public void setNewsItemTitle(String newsItemTitle) {
        this.newsItemTitle = newsItemTitle;
    }

    public String getNewsItemAuthor() {
        return newsItemAuthor;
    }

    public void setNewsItemAuthor(String newsItemAuthor) {
        this.newsItemAuthor = newsItemAuthor;
    }

    public String getNewsItemDate() {
        return newsItemDate;
    }

    public void setNewsItemDate(String newsItemDate) {
        newsItemDate = newsItemDate;
    }

    public String getNewsDescription() {
        return newsDescription;
    }
    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return Objects.equals(newsItemTitle, newsItem.newsItemTitle) &&
                Objects.equals(newsItemAuthor, newsItem.newsItemAuthor) &&
                Objects.equals(newsItemDate, newsItem.newsItemDate) &&
                Objects.equals(newsDescription, newsItem.newsDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsItemTitle, newsItemAuthor, newsItemDate, newsDescription);
    }
}

