package com.example.myapplication;

public interface ISaveSearchQueryStorage {
    void saveQuery(String query);

    String getLastQuery();
}

