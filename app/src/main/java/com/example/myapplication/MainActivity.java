package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView seriesRecyclerView;
    private ListAdapter seriesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seriesRecyclerView = findViewById(R.id.seriesRecyclerView);
        seriesListAdapter = new SeriesAdapter(new SeriesDiffCallback());
        seriesRecyclerView.setAdapter(seriesListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initModelList();
    }

    private void initModelList(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://landyrev.site/api/").addConverterFactory(GsonConverterFactory.create()).build();
        MyBackendService service = retrofit.create(MyBackendService.class);
        service.seriesList().enqueue(new Callback<List<SeriesModel>>() {
            @Override
            public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                seriesListAdapter.submitList(response.body());
            }

            @Override
            public void onFailure(Call<List<SeriesModel>> call, Throwable t) {
            }
        });
    }
}
