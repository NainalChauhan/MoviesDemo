package com.nainal.agrimovies.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private LazyLoadListener loadListener;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_LOADER = 1;
    private boolean isLoading = true;
    private View progress = null;

    public MoviesAdapter(LazyLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (i == TYPE_ITEM)
            return new MoviesHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.movies_row, parent, false));
        else
            return new LazyLoadHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.lazy_load_progress, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == TYPE_ITEM) {
            MoviesHolder holder = (MoviesHolder) viewHolder;
            final MoviesModel currentMovieModel = moviesModelList.get(i);
            holder.text_view.setText(currentMovieModel.title);
            holder.text_view_vote.setText(String.format("%s", currentMovieModel.vote_average));

            if (currentMovieModel.poster_path != null)
                Glide.with(holder.image_view.getContext())
                        .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + currentMovieModel.poster_path)
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
        }else {
            LazyLoadHolder loadHolder=(LazyLoadHolder) viewHolder;
            progress = loadHolder.lazyProgress;
//            if(moviesModelList.size() < 20)
//                loadHolder.lazyProgress.setVisibility(View.GONE);
//            else
//                loadHolder.lazyProgress.setVisibility(View.VISIBLE);
            Log.e("onBindViewHolder","TYPE_PROGRESS");
            if (!isLoading) {
                loadHolder.lazyProgress.setVisibility(View.GONE);
            } else {
                loadHolder.lazyProgress.setVisibility(View.VISIBLE);
                loadListener.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return moviesModelList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) return TYPE_LOADER;
        else return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == moviesModelList.size();
    }

    public void setModels(List<MoviesModel> movieModelList,int pageCount) {
//        this.moviesModelList = movieModelList;
//        notifyDataSetChanged();

        if (movieModelList.size() < 20) {
            isLoading = false;
            if(progress!=null)
                progress.setVisibility(View.GONE);
        } else
            isLoading = true;

        if(pageCount==1)
            this.moviesModelList.clear();

        this.moviesModelList.addAll(movieModelList);

        if (this.moviesModelList.size()>0)
            notifyItemRangeInserted(this.moviesModelList.size(),movieModelList.size());
    }

    public interface LazyLoadListener{
        void onLoadMore();
    }
}
