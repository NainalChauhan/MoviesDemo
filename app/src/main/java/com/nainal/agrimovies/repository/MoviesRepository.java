package com.nainal.agrimovies.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.AsyncTask;

import com.nainal.agrimovies.database.MoviesDao;
import com.nainal.agrimovies.database.MoviesDatabase;
import com.nainal.agrimovies.model.MoviesModel;

import java.util.List;

public class MoviesRepository {

    private MoviesDao moviesHandlerDao;
//    private LiveData<List<MoviesModel>> allLiveData;
    private LiveData<PagedList<MoviesModel>> allLiveData;

    public MoviesRepository(Application application){
        MoviesDatabase database= MoviesDatabase.getMoviesDatabaseInstance(application);
        moviesHandlerDao = database.moviesDao();
//        allLiveData = moviesHandlerDao.getAll();
        allLiveData = new LivePagedListBuilder<>(
                moviesHandlerDao.getAll(), 20).build();
    }

    public void insertAll(MoviesModel... moviesModels){
        new InsertAllModelsAsync(moviesHandlerDao).execute(moviesModels);
    }

    public void deleteAll(){
        new DeleteAllModelsAsync(moviesHandlerDao).execute();
    }

//    public LiveData<List<MoviesModel>> getAllModels(){
//        return allLiveData;
//    }
    public LiveData<PagedList<MoviesModel>> getAllModels(){
        return allLiveData;
    }

    //insert all
    private static class InsertAllModelsAsync extends AsyncTask<MoviesModel,Void,Void> {

        private MoviesDao moviesHandlerDao;

        private InsertAllModelsAsync(MoviesDao moviesHandlerDao) {
            this.moviesHandlerDao = moviesHandlerDao;
        }

        @Override
        protected Void doInBackground(MoviesModel... moviesModels) {
            moviesHandlerDao.insertAll(moviesModels);
            return null;
        }
    }

    //delete all
    private static class DeleteAllModelsAsync extends AsyncTask<Void,Void,Void>{

        private MoviesDao queryHandlerDao;

        private DeleteAllModelsAsync(MoviesDao queryHandlerDao) {
            this.queryHandlerDao = queryHandlerDao;
        }

        @Override
        protected Void doInBackground(Void... models) {
            queryHandlerDao.deleteAll();
            return null;
        }
    }
}
