package com.nainal.agrimovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nainal.agrimovies.model.MoviesModel;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM MoviesModelTable")
    DataSource.Factory<Integer, MoviesModel> getAll();
//    LiveData<List<MoviesModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MoviesModel... movies);

    @Query("DELETE FROM MoviesModelTable")
    void deleteAll();

}
