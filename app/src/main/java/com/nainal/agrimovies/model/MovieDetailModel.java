package com.nainal.agrimovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "MovieDetailTable")
public class MovieDetailModel {

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

    @SerializedName("release_date")
    @ColumnInfo
    public String release_date;

    @SerializedName("genres")
    @ColumnInfo
    public List<GenreModel> genres;

    @SerializedName("overview")
    @ColumnInfo
    public String overview;
}