package com.example.myapplication;

public interface SaveSearchQueryService {
    void saveQuery(String query);
    String getLastQuery();
}

