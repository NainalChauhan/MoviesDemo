package com.nainal.agrimovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nainal.agrimovies.model.MovieDetailModel;
import com.nainal.agrimovies.model.MoviesModel;

@Database(entities = {MoviesModel.class, MovieDetailModel.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase moviesDatabaseInstance;

    public abstract MoviesDao moviesDao();
    public abstract MovieDetailDao movieDetailDao();

    public static synchronized MoviesDatabase getMoviesDatabaseInstance(Context context){
        if(moviesDatabaseInstance ==null){
            moviesDatabaseInstance = Room.databaseBuilder(context,
                    MoviesDatabase.class, "MOVIES_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return moviesDatabaseInstance;
    }
}
