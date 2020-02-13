package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public interface SaveSearchQueryService {
    void saveQuery(String query);
    String getLastQuery();
}

