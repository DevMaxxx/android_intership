package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSearchQueryFromSharedPreferences implements SaveSearchQueryService {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SaveSearchQueryFromSharedPreferences(Context context) {
        preferences = context.getSharedPreferences("QUERY_STORE", context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    public void saveQuery(String query) {
        editor.putString("LAST_QUERY",query);
        editor.apply();
    }

    @Override
    public String getLastQuery() {
        return preferences.getString("LAST_QUERY","");
    }
}
