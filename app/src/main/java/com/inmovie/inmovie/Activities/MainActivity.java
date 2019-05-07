package com.inmovie.inmovie.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.HandlingTrending;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.SideSpaceItemDecoration;
import com.inmovie.inmovie.SpacesItemDecoration;
import com.inmovie.inmovie.model.api.TMDb.Trending.GetTrendingMovies;
import com.inmovie.inmovie.model.api.TMDb.Trending.GetTrendingTV;

public class MainActivity extends AppCompatActivity {

    private RecyclerView trendingMovies;
    private TrendingsAdapter movieTrendingsAdapter;

    private RecyclerView trendingShows;
    private TrendingsAdapter showTrendingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SearchView searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setSubmitButtonEnabled(true);
        searchBar.setFocusable(false);
        searchBar.setIconifiedByDefault(false);
        searchBar.clearFocus();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, MoviesSearchResult.class);
                intent.putExtra("movie_searched", query);
                MainActivity.this.startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        movieTrendingsAdapter = new TrendingsAdapter(this);
        showTrendingsAdapter = new TrendingsAdapter(this);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing1);

        trendingMovies = findViewById(R.id.movie_trending);
        trendingShows = findViewById(R.id.tv_trending);

        trendingMovies.setHasFixedSize(true);
        trendingMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        trendingMovies.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels));
        trendingMovies.setAdapter(movieTrendingsAdapter);

        trendingShows.setHasFixedSize(true);
        trendingShows.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        trendingShows.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels));
        trendingShows.setAdapter(showTrendingsAdapter);

        new GetTrendingMovies(movieTrendingsAdapter).execute();
        new GetTrendingTV(showTrendingsAdapter).execute();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SearchView searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setSubmitButtonEnabled(true);
        searchBar.setFocusable(false);
        searchBar.setIconifiedByDefault(false);
        searchBar.clearFocus();
    }

    public void startPopularView(View view){
        Intent intent = new Intent(this, PopularMovies.class);
        startActivity(intent);
    }

}
