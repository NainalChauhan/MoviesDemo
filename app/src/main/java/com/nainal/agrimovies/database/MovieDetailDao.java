package com.nainal.agrimovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nainal.agrimovies.model.MovieDetailModel;


@Dao
public interface MovieDetailDao {

    @Query("SELECT * FROM MovieDetailTable")
    LiveData<MovieDetailModel> getMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieDetailModel movieDetailModel);

    @Query("DELETE FROM MovieDetailTable")
    void deleteAllMovie();

}
