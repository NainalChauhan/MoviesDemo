package com.nainal.agrimovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "MoviesModelTable")
public class MoviesModel {

//    @PrimaryKey(autoGenerate = true)
//    public int pId;

    @SerializedName("title")
    @ColumnInfo
    public String title;

    @SerializedName("poster_path")
    @ColumnInfo
    public String poster_path;

    @SerializedName("id")
    @PrimaryKey
    public int id;

    @SerializedName("vote_average")
    @ColumnInfo
    public float vote_average;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoviesModel)) return false;
        MoviesModel that = (MoviesModel) o;
        return id == that.id;
    }
}