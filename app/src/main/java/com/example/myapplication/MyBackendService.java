package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyBackendService {
    @GET("series")
    public Call<List<SeriesModel>> seriesList();
}
