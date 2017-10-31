package com.re_coded.example.android.newsapp;

/**
 * Created by Lenovo on 10/30/2017.
 */

public class News {
    private String title;
    private String section;
    private String author;
    private String date;
    private String URL;

    public News(String title, String section, String author, String date, String URL) {
        this.title = title;
        this.section = section;
        this.author = author;
        this.date = date;
        this.URL = URL;
    }

    public String getTitle() {
        return title;
    }

    public String getURL() {
        return URL;
    }

    public String getSection() {
        return section;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}
