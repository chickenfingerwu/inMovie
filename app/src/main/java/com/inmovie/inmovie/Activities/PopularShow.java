package com.inmovie.inmovie.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.inmovie.inmovie.Adapters.EndlessScrollListener;
import com.inmovie.inmovie.Adapters.PopularShowAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.Decorations.SpacesItemDecoration;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;
import com.inmovie.inmovie.model.api.MoviesApiService;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PopularShow extends AppCompatActivity {
    private EndlessScrollListener scrollListener;
    private RecyclerView recyclerView;
    private PopularShowAdapter popularShowAdapter;
    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_show);
        //Toolbar myToolbar = (Toolbar) findViewById(R.id.homepage_toolbar);
        //setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.show_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 3);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.setLayoutManager(layoutManager);

        popularShowAdapter = new PopularShowAdapter(this);
        recyclerView.setAdapter(popularShowAdapter);

        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        getMovieListFromRestAdapter(1);
        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getMovieListFromRestAdapter(++page);

            }
        };

        recyclerView.addOnScrollListener(scrollListener);

    }


    private void getMovieListFromRestAdapter(final int page){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.TMDb_API_key);
                        request.addEncodedQueryParam("page", Integer.toString(page));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getTrendingShow(new Callback<TvShow.TvShowResult>() {
            @Override
            public void success(TvShow.TvShowResult movieResult, Response response) {
                popularShowAdapter.setShowList(movieResult.getResults(), false);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
