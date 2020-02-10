package com.example.myapplication;

import com.google.gson.annotations.Expose;

public class SeriesModel {

    public  int id;
    public String posterBase64;
    public String title;
    public String description;

    public SeriesModel(int id, String posterBase64, String title, String description) {
        this.id = id;
        this.posterBase64 = posterBase64;
        this.title = title;
        this.description = description;
    }
}
