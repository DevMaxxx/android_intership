package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class SeriesDiffCallback extends DiffUtil.ItemCallback<SeriesModel> {
    @Override
    public boolean areItemsTheSame(@NonNull SeriesModel oldItem, @NonNull SeriesModel newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull SeriesModel oldItem, @NonNull SeriesModel newItem) {
        return oldItem.posterBase64.equals(newItem.posterBase64) && oldItem.title.equals(newItem.title) && oldItem.description.equals(newItem.description);
    }
}
