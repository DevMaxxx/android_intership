package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSearchQueryFromSharedPreferences implements SaveSearchQueryStorage {

    private SharedPreferences preferences;

    private static final String QUEERY_STORE = "QUERY_STORE";
    private static final String LAST_STRING_ID = "LAST_STRING";

    public SaveSearchQueryFromSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(QUEERY_STORE, context.MODE_PRIVATE);
    }

    @Override
    public void saveQuery(String query) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LAST_STRING_ID, query);
        editor.apply();
    }

    @Override
    public String getLastQuery() {
        return preferences.getString("LAST_QUERY", "");
    }
}
