package com.nainal.agrimovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "GenreModelTable")
public class GenreModel {

    @PrimaryKey(autoGenerate = true)
    public int pId;

    @SerializedName("name")
    @ColumnInfo
    public String name;

    @SerializedName("id")
    @ColumnInfo
    public int id;
}