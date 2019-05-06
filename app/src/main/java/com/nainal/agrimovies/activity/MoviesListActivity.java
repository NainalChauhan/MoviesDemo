package com.nainal.agrimovies.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.nainal.agrimovies.R;
import com.nainal.agrimovies.adapter.MoviesAdapter;
import com.nainal.agrimovies.model.MoviesListModel;
import com.nainal.agrimovies.model.MoviesModel;
import com.nainal.agrimovies.servicehandlers.MoviesServices;
import com.nainal.agrimovies.servicehandlers.ServiceGenerator;
import com.nainal.agrimovies.utility.Utility;
import com.nainal.agrimovies.viewmodel.MoviesViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListActivity extends AppCompatActivity {

    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;
    private MoviesViewModel moviesViewModel;
    private ProgressBar moviesProgressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesRecyclerView = findViewById(R.id.recycler_view_movies);
        moviesProgressBar = findViewById(R.id.progress_bar);

        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        moviesRecyclerView.setAdapter(moviesAdapter);

        if (Utility.isInternetConnected(this)) {
            //if internet is connected
            moviesProgressBar.setVisibility(View.VISIBLE);
            Call<MoviesListModel> listMoviesCall= ServiceGenerator.createService(MoviesServices.class).listMovies(
                    ServiceGenerator.API_KEY,"en-US","popularity.desc",false,
                    false,"2019-04-01","2019-05-07",1);

            listMoviesCall.enqueue(new Callback<MoviesListModel>() {
                @Override
                public void onResponse(Call<MoviesListModel> call, Response<MoviesListModel> response) {
                    MoviesModel[] responseData = new MoviesModel[response.body().results.size()];
                    response.body().results.toArray(responseData);

//                    MoviesModel[] responseData = (MoviesModel []) response.body().results.toArray();
                    Log.e("TAG", "onResponse: "+responseData[0].poster_path );
                    moviesViewModel.deleteAll();
                    moviesViewModel.insertAll(responseData);
                    startCallback();
                }

                @Override
                public void onFailure(Call<MoviesListModel> call, Throwable t) {
                    startCallback();
                }
            });
        }else startCallback();
    }

    public void startCallback(){
        moviesViewModel.getAllModel().observe(this, new Observer<List<MoviesModel>>() {
            @Override
            public void onChanged(@Nullable List<MoviesModel> moviesModels) {
                //update recycler view from here
                moviesAdapter.setModels(moviesModels);
                if (moviesModels != null && moviesModels.size() > 0)
                    moviesProgressBar.setVisibility(View.GONE);
            }
        });
    }

}
