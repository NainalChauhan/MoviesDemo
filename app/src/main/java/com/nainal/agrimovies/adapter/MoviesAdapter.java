package com.nainal.agrimovies.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nainal.agrimovies.R;
import com.nainal.agrimovies.model.MoviesModel;
import com.nainal.agrimovies.utility.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends PagedListAdapter<MoviesModel,MoviesHolder> {

//    private List<MoviesModel> moviesModelList = new ArrayList<>();
    private LazyLoadListener loadListener;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_LOADER = 1;
    private boolean isLoading = true;
    private View progress = null;

    public MoviesAdapter() {
        super(DIFF_CALLBACK);
    }

//    public MoviesAdapter(LazyLoadListener loadListener) {
//        this.loadListener = loadListener;
//    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        if (i == TYPE_ITEM)
            return new MoviesHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.movies_row, parent, false));
//        else
//            return new LazyLoadHolder(LayoutInflater.from(
//                    parent.getContext()).inflate(R.layout.lazy_load_progress, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder moviesHolder, int i) {
        Log.e("TAG", "onBindViewHolder: " );
        MoviesModel currentMovieModel=getItem(i);
        if(currentMovieModel!=null){

            moviesHolder.text_view.setText(currentMovieModel.title);
            moviesHolder.text_view_vote.setText(String.format("%s", currentMovieModel.vote_average));

            if (currentMovieModel.poster_path != null)
                GlideApp.with(moviesHolder.image_view.getContext())
                        .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + currentMovieModel.poster_path)
                        .centerCrop()
                        .into(moviesHolder.image_view);
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        Log.e("TAG", "onBindViewHolder: " );
//         MoviesModel currentMovieModel=getItem(i);
//         if(currentMovieModel!=null){
//             MoviesHolder holder = (MoviesHolder) viewHolder;
//            holder.text_view.setText(currentMovieModel.title);
//            holder.text_view_vote.setText(String.format("%s", currentMovieModel.vote_average));
//
//            if (currentMovieModel.poster_path != null)
//                GlideApp.with(holder.image_view.getContext())
//                        .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + currentMovieModel.poster_path)
//                        .centerCrop()
//                        .into(holder.image_view);
//         }
////        if (getItemViewType(i) == TYPE_ITEM) {
////            MoviesHolder holder = (MoviesHolder) viewHolder;
////            final MoviesModel currentMovieModel = moviesModelList.get(i);
////            holder.text_view.setText(currentMovieModel.title);
////            holder.text_view_vote.setText(String.format("%s", currentMovieModel.vote_average));
////
////            if (currentMovieModel.poster_path != null)
////                GlideApp.with(holder.image_view.getContext())
////                        .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + currentMovieModel.poster_path)
////                        .centerCrop()
////                        .into(holder.image_view);
////
////            holder.card_view.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
//////                if ( currentMovieModel.id > 0)
//////                    (v.getContext()).startActivity(new Intent(v.getContext(), MeetingsActivity.class)
//////                            .putExtra(Constants.NAME, currentMovieModel.name)
//////                            .putExtra(Constants.LOCATION, currentMovieModel.location)
//////                            .putExtra(Constants.ID, currentMovieModel.id));
////                }
////            });
////        }else {
////            LazyLoadHolder loadHolder=(LazyLoadHolder) viewHolder;
////            progress = loadHolder.lazyProgress;
//////            if(moviesModelList.size() < 20)
//////                loadHolder.lazyProgress.setVisibility(View.GONE);
//////            else
//////                loadHolder.lazyProgress.setVisibility(View.VISIBLE);
////            Log.e("onBindViewHolder","TYPE_PROGRESS");
////            if (!isLoading) {
////                loadHolder.lazyProgress.setVisibility(View.GONE);
////            } else {
////                if(moviesModelList.size()>20)
////                    loadHolder.lazyProgress.setVisibility(View.VISIBLE);
////                loadListener.onLoadMore();
////            }
////        }
//    }

//    @Override
//    public int getItemCount() {
//        return moviesModelList.size();
//    }

//    @Override
//    public int getItemViewType(int position) {
//        if (isPositionFooter(position)) return TYPE_LOADER;
//        else return TYPE_ITEM;
//    }
//
//    private boolean isPositionFooter(int position) {
//        return position == moviesModelList.size();
//    }

//    public void setModels(List<MoviesModel> movieModelList,int pageCount) {
//        Log.e("TAG", "setModels: "+movieModelList.size() );
//
//        if (movieModelList.size() < 20) {
//            isLoading = false;
//            if(progress!=null)
//                progress.setVisibility(View.GONE);
//        } else
//            isLoading = true;
//
////        if(pageCount==1)
////            this.moviesModelList.clear();
//
//        this.moviesModelList.addAll(movieModelList);
//
//        if (this.moviesModelList.size()>0)
//            notifyItemRangeInserted(this.moviesModelList.size(),movieModelList.size());
//
//        Log.e("TAG", "moviesModelList: "+moviesModelList.size() );
//    }

    public interface LazyLoadListener{
        void onLoadMore();
    }

    private static DiffUtil.ItemCallback<MoviesModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MoviesModel>() {
                // MoviesModel details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(MoviesModel oldMovie, MoviesModel newMovie) {
                    return oldMovie.id == newMovie.id;
                }

                @Override
                public boolean areContentsTheSame(MoviesModel oldMovie,
                                                  MoviesModel newMovie) {
                    return oldMovie.equals(newMovie);
                }
            };
}
