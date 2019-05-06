package com.nainal.agrimovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "MoviesModelTable")
public class MoviesModel {

    @PrimaryKey(autoGenerate = true)
    public int pId;

    @SerializedName("title")
    @ColumnInfo
    public String title;

    @SerializedName("poster_path")
    @ColumnInfo
    public String poster_path;

    @SerializedName("id")
    @ColumnInfo
    public int id;

    @SerializedName("vote_average")
    @ColumnInfo
    public float vote_average;
}