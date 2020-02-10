package com.example.myapplication;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SeriesAdapter extends ListAdapter<SeriesModel, SeriesAdapter.SeriesViewHolder> {
    protected SeriesAdapter(@NonNull DiffUtil.ItemCallback<SeriesModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.series_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        holder.Bind(getItem(position));
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder{

        final ImageView poster;
        final TextView title;
        final TextView description;
        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
        public void Bind(SeriesModel model){
            byte[] b = Base64.decode(model.posterBase64,Base64.DEFAULT);
            poster.setImageBitmap(BitmapFactory.decodeByteArray(b,0,b.length));
            title.setText(model.title);
            description.setText(model.description);
        }
    }
}
