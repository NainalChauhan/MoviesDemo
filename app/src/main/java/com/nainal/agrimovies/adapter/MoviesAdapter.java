package com.nainal.agrimovies.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nainal.agrimovies.R;
import com.nainal.agrimovies.model.MoviesModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MoviesModel> moviesModelList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MoviesHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.movies_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MoviesHolder holder = (MoviesHolder) viewHolder;
        final MoviesModel currentMovieModel = moviesModelList.get(i);
        holder.text_view.setText(currentMovieModel.title);

        if(currentMovieModel.poster_path!=null)
            Glide.with(holder.image_view.getContext())
                .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+currentMovieModel.poster_path)
                .centerCrop()
                .into(holder.image_view);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if ( currentMovieModel.id > 0)
//                    (v.getContext()).startActivity(new Intent(v.getContext(), MeetingsActivity.class)
//                            .putExtra(Constants.NAME, currentMovieModel.name)
//                            .putExtra(Constants.LOCATION, currentMovieModel.location)
//                            .putExtra(Constants.ID, currentMovieModel.id));
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesModelList.size();
    }

    public void setModels(List<MoviesModel> movieModelList) {
        this.moviesModelList = movieModelList;
        notifyDataSetChanged();
    }
}
