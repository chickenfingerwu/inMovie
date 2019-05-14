package com.inmovie.inmovie.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.inmovie.inmovie.Activities.MoviesActivities.MovieDetails;
import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.Handlers.HandlingTrending;
import com.inmovie.inmovie.MovieTvClasses.Movies;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.Decorations.SideSpaceItemDecoration;
import com.inmovie.inmovie.model.api.TMDb.Trending.GetTrendingMovies;
import com.inmovie.inmovie.model.api.TMDb.Trending.GetTrendingTV;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private HandlingTrending handler;

    private RecyclerView trendingMovies;
    private TrendingsAdapter movieTrendingsAdapter;

    private RecyclerView trendingShows;
    private TrendingsAdapter showTrendingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollView scrollView = (ScrollView) findViewById(R.id.main_activity_view);
        if(scrollView!=null) scrollView.requestFocus();

        handler = new HandlingTrending(this);

        View current = getCurrentFocus();
        if (current != null) current.clearFocus();

        SearchView searchBar = (SearchView) findViewById(R.id.searchBar);
        if(searchBar!=null) {
            searchBar.setSubmitButtonEnabled(true);
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

        }
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

        new GetTrendingMovies(movieTrendingsAdapter, handler).execute();
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

    public void startPopularShow(View view){
        Intent intent = new Intent(this, PopularShow.class);
        startActivity(intent);
    }

    public void setHottestTrendingMovieBanner(Movies movies){
        ImageView banner = (ImageView) findViewById(R.id.image_banner);
        TextView hottestName = (TextView) findViewById(R.id.featured_movie_name);
        if(movies != null) {
            if (movies.getBackdrop() != null) {
                Picasso.with(this)
                        .load(movies.getBackdrop())
                        .noFade()
                        .placeholder(R.color.grey)
                        .into(banner);
            }
            String[] release = movies.getReleaseDate().split("-");
            String source = movies.getTitle() + " (" + release[0] + ")";
            hottestName.setText(source);
        }
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(banner.getContext(), MovieDetails.class);
                intent.putExtra("serialize_data", movies);
                banner.getContext().startActivity(intent);
            }
        });
    }
}
