package com.nainal.agrimovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nainal.agrimovies.model.MovieDetailModel;
import com.nainal.agrimovies.model.MoviesModel;
import com.nainal.agrimovies.repository.MovieDetailsRepository;
import com.nainal.agrimovies.repository.MoviesRepository;

import java.util.List;

public class MovieDetailViewModel extends AndroidViewModel {

    private MovieDetailsRepository moviesRepository;
    private LiveData<MovieDetailModel> allModel;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MovieDetailsRepository(application);
        allModel = moviesRepository.getMovieDetails();
    }

    public void insertMovie(MovieDetailModel movieModel){
        moviesRepository.insertAll(movieModel);
    }

    public void deleteAll(){
        moviesRepository.deleteAll();
    }

    public LiveData<MovieDetailModel> getAllModel(){
        return allModel;
    }
}
