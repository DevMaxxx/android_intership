package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SeriesAdapter seriesListAdapter;

    private Button series_search_button;
    private EditText series_search_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView seriesRecyclerView = findViewById(R.id.seriesRecyclerView);
        seriesListAdapter = new SeriesAdapter(new SeriesDiffCallback());
        seriesRecyclerView.setAdapter(seriesListAdapter);

        series_search_button = findViewById(R.id.series_search_button);
        series_search_text = findViewById(R.id.series_search_text);

        initServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initSeriesList();

        series_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchButtonClick();
            }
        });
        series_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onSearchTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void setSeriesRecyclerViewContent(List<SeriesModel> content) {
        seriesListAdapter.submitList(content);
    }

    private void onSeriesNotFound() {
        Toast.makeText(this, R.string.series_not_found, Toast.LENGTH_SHORT).show();
    }

    private void setSeriesSearchText(String query) {
        series_search_text.setText(query);
    }
//--------------------------------PrePresenter---------------


    private String searchQueryString;
    private List<SeriesModel> allSeries;
    private MyBackendService myBackendService;

    private ISaveSearchQueryStorage saveSearchQueryService;

    private void initServices() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://landyrev.site/api/").addConverterFactory(GsonConverterFactory.create()).build();
        myBackendService = retrofit.create(MyBackendService.class);
        saveSearchQueryService = new SaveSearchQueryFromSharedPreferences(this);
    }

    private void initSeriesList() {
        myBackendService.seriesList().enqueue(new Callback<List<SeriesModel>>() {
            @Override
            public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                allSeries = response.body();
            }

            @Override
            public void onFailure(Call<List<SeriesModel>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Internal error", Toast.LENGTH_SHORT).show();
            }
        });

        final String lastQuery = saveSearchQueryService.getLastQuery();

        if (lastQuery.equals("")) {
            setSeriesRecyclerViewContent(allSeries);
        } else {
            setSeriesSearchText(lastQuery);
            myBackendService.searchResult(lastQuery).enqueue(new Callback<List<SeriesModel>>() {
                @Override
                public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                    setSeriesRecyclerViewContent(response.body());
                }

                @Override
                public void onFailure(Call<List<SeriesModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Internal error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onSearchButtonClick() {
        myBackendService.searchResult(searchQueryString).enqueue(new Callback<List<SeriesModel>>() {
            @Override
            public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                if (response.body().isEmpty()) {
                    onSeriesNotFound();
                } else {
                    saveSearchQueryService.saveQuery(searchQueryString);
                }
                setSeriesRecyclerViewContent(response.body());
            }

            @Override
            public void onFailure(Call<List<SeriesModel>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Internal error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onSearchTextChanged(String newText) {
        searchQueryString = newText;
        if (searchQueryString.isEmpty()) {
            series_search_button.setClickable(false);
            setSeriesRecyclerViewContent(allSeries);
        } else {
            series_search_button.setClickable(true);
        }
    }
}
