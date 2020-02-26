package com.example.myapplication;

import java.util.List;

public interface IMainView {
    void setSeriesViewContent(List<SeriesModel> content);
    void setSeriesFindText(String query);
    void setSeriesFindButtonActive(boolean isEnables);

    void onEroorNotification(String error);
}
