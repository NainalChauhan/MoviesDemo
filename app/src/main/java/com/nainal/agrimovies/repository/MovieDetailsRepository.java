package com.nainal.agrimovies.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.nainal.agrimovies.database.MovieDetailDao;
import com.nainal.agrimovies.database.MoviesDao;
import com.nainal.agrimovies.database.MoviesDatabase;
import com.nainal.agrimovies.model.MovieDetailModel;
import com.nainal.agrimovies.model.MoviesModel;

import java.util.List;

public class MovieDetailsRepository {

    private MovieDetailDao moviesHandlerDao;
    private LiveData<MovieDetailModel> allLiveData;

    public MovieDetailsRepository(Application application){
        MoviesDatabase database= MoviesDatabase.getMoviesDatabaseInstance(application);
        moviesHandlerDao = database.movieDetailDao();
        allLiveData = moviesHandlerDao.getMovie();
    }

    public void insertAll(MovieDetailModel movieModel){
        new InsertModelsAsync(moviesHandlerDao).execute(movieModel);
    }

    public void deleteAll(){
        new DeleteAllModelsAsync(moviesHandlerDao).execute();
    }

    public LiveData<MovieDetailModel> getMovieDetails(){
        return allLiveData;
    }

    //insert all
    private static class InsertModelsAsync extends AsyncTask<MovieDetailModel,Void,Void> {

        private MovieDetailDao moviesHandlerDao;

        private InsertModelsAsync(MovieDetailDao moviesHandlerDao) {
            this.moviesHandlerDao = moviesHandlerDao;
        }

        @Override
        protected Void doInBackground(MovieDetailModel... movieDetailModels) {
            moviesHandlerDao.insertMovie(movieDetailModels[0]);
            return null;
        }
    }

    //delete all
    private static class DeleteAllModelsAsync extends AsyncTask<Void,Void,Void>{

        private MovieDetailDao queryHandlerDao;

        private DeleteAllModelsAsync(MovieDetailDao queryHandlerDao) {
            this.queryHandlerDao = queryHandlerDao;
        }

        @Override
        protected Void doInBackground(Void... models) {
            queryHandlerDao.deleteAllMovie();
            return null;
        }
    }
}
