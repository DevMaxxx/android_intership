package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class MainPresenter implements IMainPresenter {

    private final IMainView view;
    private final ISaveSearchQueryStorage queryStorage;
    private final MyBackendService backendService;
    public MainPresenter(IMainView view) {
        this.view = view;
        this.queryStorage = new SaveSearchQueryFromSharedPreferences();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://landyrev.site/api/").addConverterFactory(GsonConverterFactory.create()).build();
        backendService = retrofit.create(MyBackendService.class);
    }

    @Override
    public void onSearchButtonClick() {

    }

    @Override
    public void onSearchTextChanged(String newText) {

    }
}
