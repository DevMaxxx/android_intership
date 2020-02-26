package com.example.myapplication;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements IMainPresenter {

    private final IMainView view;
    private final ISaveSearchQueryStorage queryStorage;
    private final MyBackendService backendService;
    private String queryString;
    private List<SeriesModel> allSeries;


    public MainPresenter(final IMainView view, Context context) {
        this.view = view;
        this.queryStorage = new SaveSearchQueryFromSharedPreferences(context);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://landyrev.site/api/").addConverterFactory(GsonConverterFactory.create()).build();
        this.backendService = retrofit.create(MyBackendService.class);

        backendService.seriesList().enqueue(new Callback<List<SeriesModel>>() {
            @Override
            public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                allSeries = response.body();
            }

            @Override
            public void onFailure(Call<List<SeriesModel>> call, Throwable t) {
                view.onEroorNotification("Internal error");
            }
        });
        queryString = queryStorage.getLastQuery();
        if(!queryString.equals("")){
            searchAndSet();
            view.setSeriesFindText(queryString);
        }else{
            view.setSeriesViewContent(allSeries);
        }
    }

    @Override
    public void onSearchButtonClick() {
        queryStorage.saveQuery(queryString);
        searchAndSet();
    }
    private void searchAndSet(){
        backendService.searchResult(queryString).enqueue(new Callback<List<SeriesModel>>() {
            @Override
            public void onResponse(Call<List<SeriesModel>> call, Response<List<SeriesModel>> response) {
                if (!response.body().isEmpty()) {
                    queryStorage.saveQuery(queryString);
                    view.setSeriesViewContent(response.body());
                } else {
                    view.onEroorNotification("Search not found");
                }
            }

            @Override
            public void onFailure(Call<List<SeriesModel>> call, Throwable t) {
                view.onEroorNotification("Internal error");
            }
        });
    }

    @Override
    public void onSearchTextChanged(String newText) {
        queryString = newText;
        if(queryString.equals("")){
            view.setSeriesViewContent(allSeries);
            view.setSeriesFindButtonActive(false);
        }else{
            view.setSeriesFindButtonActive(true);
        }
    }
}
