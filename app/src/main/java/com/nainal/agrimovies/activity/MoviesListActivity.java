package com.nainal.agrimovies.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private int pageCount=1;
    private boolean isLoading=false;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesRecyclerView = findViewById(R.id.recycler_view_movies);
        moviesProgressBar = findViewById(R.id.progress_bar);

        moviesAdapter = new MoviesAdapter();
//        moviesRecyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                switch(moviesAdapter.getItemViewType(position)){
//                    case MoviesAdapter.TYPE_ITEM:
//                        return 1;
//                    case MoviesAdapter.TYPE_LOADER:
//                        return 2; //number of columns of the grid
//                    default:
//                        return -1;
//                }
//            }
//        });
        moviesRecyclerView.setLayoutManager(gridLayoutManager);
        moviesRecyclerView.setAdapter(moviesAdapter);
        moviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = gridLayoutManager.getItemCount();
                int visibleItemCount = gridLayoutManager.getChildCount();
                int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

                if ( (!isLoading) && lastVisibleItem + visibleThreshold >= totalItemCount) {
                    isLoading=true;
                    Log.e("TAG", "onScrolled "+visibleItemCount+"//"+lastVisibleItem+"//"+totalItemCount );
                    getMovieListFromService("popularity.desc");
                }
            }
        });
        moviesViewModel.getAllModel().observe(this, new Observer<PagedList<MoviesModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<MoviesModel> moviesModels) {
                Log.e("TAG", "onChanged: "+moviesModels.size() );
                moviesAdapter.submitList(moviesModels);
            }
        });
        getMovieListFromService("popularity.desc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_sort_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sort_rating){
            pageCount=1;
            getMovieListFromService("vote_average.desc");
        }
        else if(item.getItemId()==R.id.sort_date){
            pageCount=1;
            getMovieListFromService("primary_release_date.desc");
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMovieListFromService(String sort_by){
        if (Utility.isInternetConnected(this)) {
            //if internet is connected
            Log.e("TAG", "pageCount: "+pageCount );
            if(pageCount==1)
                moviesProgressBar.setVisibility(View.GONE);
            Call<MoviesListModel> listMoviesCall= ServiceGenerator.createService(MoviesServices.class).listMovies(
                    ServiceGenerator.API_KEY,"en-US",sort_by,false,
                    false,"2019-04-01","2019-05-07",pageCount);

            listMoviesCall.enqueue(new Callback<MoviesListModel>() {
                @Override
                public void onResponse(@NonNull Call<MoviesListModel> call,@NonNull Response<MoviesListModel> response) {

                    if(response.body()!=null){
                        Log.e("TAG", "onResponse: "+response.body().results.size() );

                        MoviesModel[] responseData = new MoviesModel[response.body().results.size()];
                        response.body().results.toArray(responseData);

                        if(pageCount==1){
//                            moviesViewModel.deleteAll();
                        }
                        moviesViewModel.insertAll(responseData);
                        pageCount++;
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MoviesListModel> call,@NonNull Throwable t) {

                }
            });
        }else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();

        }
    }


    public void startCallback(){
//        moviesViewModel.getAllModel().observe(this, new Observer<List<MoviesModel>>() {
//            @Override
//            public void onChanged(@Nullable List<MoviesModel> moviesModels) {
//                //update recycler view from here
//                for (MoviesModel m:
//                     moviesModels) {
//                    Log.e("TAG", "id: "+m.id );
//                }
//                Log.e("TAG", "onChanged: calleddddd "+moviesModels.size() );
//                moviesAdapter.setModels(moviesModels,currentPageCount);
//                if (moviesModels != null && moviesModels.size() > 0)
//                    moviesProgressBar.setVisibility(View.GONE);
//            }
//        });

    }
}
