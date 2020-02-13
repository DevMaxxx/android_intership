package com.example.myapplication;

public interface SaveSearchQueryStorage {
    void saveQuery(String query);

    String getLastQuery();
}

