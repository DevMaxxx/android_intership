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

public class MainActivity extends AppCompatActivity implements IMainView {

    private SeriesAdapter seriesListAdapter;

    private Button series_search_button;
    private EditText series_search_text;

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView seriesRecyclerView = findViewById(R.id.seriesRecyclerView);
        seriesListAdapter = new SeriesAdapter(new SeriesDiffCallback());
        seriesRecyclerView.setAdapter(seriesListAdapter);

        series_search_button = findViewById(R.id.series_search_button);
        series_search_text = findViewById(R.id.series_search_text);

        presenter = new MainPresenter(this,this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        series_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSearchButtonClick();
            }
        });
        series_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onSearchTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setSeriesViewContent(List<SeriesModel> content) {
        seriesListAdapter.submitList(content);
    }

    @Override
    public void setSeriesFindText(String query) {
        series_search_text.setText(query);
    }

    @Override
    public void setSeriesFindButtonActive(boolean isEnables) {
        series_search_button.setClickable(true);
    }

    @Override
    public void onEroorNotification(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
