package com.nainal.agrimovies.servicehandlers;

import com.nainal.agrimovies.model.MoviesListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesServices {

    @GET("discover/movie")
    Call<MoviesListModel> listMovies(@Query("api_key") String api_key,
                                     @Query("language") String language,
                                     @Query("sort_by") String sort_by,
                                     @Query("include_adult") boolean include_adult,
                                     @Query("include_video") boolean include_video,
                                     @Query("primary_release_date.gte") String primary_release_date_gte,
                                     @Query("primary_release_date.lte") String primary_release_date_lte,
                                     @Query("page") int page);
}
