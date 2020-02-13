package com.example.myapplication;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyBackendService {
    @GET("series")
    Call<List<SeriesModel>> seriesList();

    @GET("search")
    Call<List<SeriesModel>> searchResult(@Query("q") @NonNull String query);
}
