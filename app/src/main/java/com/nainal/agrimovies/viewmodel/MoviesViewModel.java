package com.nainal.agrimovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nainal.agrimovies.model.MoviesModel;
import com.nainal.agrimovies.repository.MoviesRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesRepository moviesRepository;
    private LiveData<List<MoviesModel>> allModel;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        allModel = moviesRepository.getAllModels();
    }

    public void insertAll(MoviesModel... moviesModels){
        moviesRepository.insertAll(moviesModels);
    }

    public void deleteAll(){
        moviesRepository.deleteAll();
    }

    public LiveData<List<MoviesModel>> getAllModel(){
        return allModel;
    }
}
