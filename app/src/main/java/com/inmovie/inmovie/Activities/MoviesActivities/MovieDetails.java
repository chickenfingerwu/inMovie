package com.inmovie.inmovie.Activities.MoviesActivities;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.HandlingMovie;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.SideSpaceItemDecoration;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetDetails;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetReviews;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetSimilarMovies;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetVideos;
import com.victor.loading.rotate.RotateLoading;


import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;



/* This class implements the Movie's details activity,
user will see this when choosing a movie
 */

public class MovieDetails extends AppCompatActivity {

    protected int reviewPage = 1;

    private ProgressBar rotateLoading;

    private HandlingMovie handlingMovie;
    private LinearLayout reviewsList;
    //Adapters to display data of cast and crew
    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;
    private TrendingsAdapter similarMoviesAdapter;

    //RecyclerView is where Adapters will display data onto
    private RecyclerView castList;
    private RecyclerView crewList;
    private RecyclerView similarMovies;

    //LayoutManager to determine the layout of the RecyclerView
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager crewLayoutManager;
    private RecyclerView.LayoutManager similarMovieLayoutManager;

    //this Movie instance holds the selected movie data
    private Movies movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        reviewsList = (LinearLayout) findViewById(R.id.movie_reviews);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Get Intent that started this activity and extract selected data (a movie in this case) from previous activity
        Intent intent = getIntent();
        movie = (Movies) intent.getSerializableExtra("serialize_data");

        handlingMovie = new HandlingMovie(this);

        /*AppBarLayout bannerLayout = (AppBarLayout) findViewById(R.id.movie_banner);
        bannerLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                } else if (isShow) {
                    isShow = false;
                }
            }
        });*/

        castList = (RecyclerView) findViewById(R.id.cast_list);
        //Create a horizontal layout for the RecyclerView
        castLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        castAdapter = new CastListAdapters(this);

        castList.setHasFixedSize(true);
        castList.setLayoutManager(castLayoutManager);
        castList.setAdapter(castAdapter);

        crewList = (RecyclerView) findViewById(R.id.crew_list);
        crewLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        crewAdapter = new CrewListAdapters(this);

        crewList.setHasFixedSize(true);
        crewList.setAdapter(crewAdapter);
        crewList.setLayoutManager(crewLayoutManager);

        similarMovies = (RecyclerView) findViewById(R.id.suggest_movie_list);
        similarMovieLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        similarMoviesAdapter = new TrendingsAdapter(this);


        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing2);
        similarMovies.setHasFixedSize(true);
        similarMovies.setAdapter(similarMoviesAdapter);
        similarMovies.setLayoutManager(similarMovieLayoutManager);
        similarMovies.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels));

        int spacingInPixels1 = getResources().getDimensionPixelSize(R.dimen.spacingCast);
        castList.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels1));
        crewList.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels1));

        TextView toggle = findViewById(R.id.movie_button_toggle);
        toggle.setText(R.string.expand);

        ExpandableTextView overview = findViewById(R.id.movieDescription);
        overview.setInterpolator(new OvershootInterpolator());
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle.setText(overview.isExpanded() ? R.string.expand : R.string.collapse);
                overview.toggle();
            }
        });
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle.setText(overview.isExpanded() ? R.string.expand : R.string.collapse);
                overview.toggle();
            }
        });

        TextView seeMoreReviews = (TextView) findViewById(R.id.loadMoreButton);
        rotateLoading = (ProgressBar) findViewById(R.id.rotateLoading);
        seeMoreReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateLoading.setVisibility(View.VISIBLE);
                getMoreReview();
            }

        });

        ImageView banner = findViewById(R.id.poster_banner);

        TextView contentName = findViewById(R.id.content_name);

        TextView numberRating = findViewById(R.id.numberRating);

        RatingBar ratingBar = findViewById(R.id.ratingBar3);
        ratingBar.setNumStars(10);

        TextView additionalInfo = (TextView) findViewById(R.id.additionalInfo);
        TextView genres_runtime = (TextView) findViewById(R.id.genres_runtime);

        TextView releseDate = (TextView) findViewById(R.id.release_date);

        ArrayList<View> views = new ArrayList<>();
        views.add(contentName);
        views.add(overview);
        views.add(banner);
        views.add(numberRating);
        views.add(ratingBar);
        views.add(additionalInfo);
        views.add(genres_runtime);
        views.add(releseDate);

        contentName.setTextColor(Color.WHITE);

        new GetDetails(views).execute(movie.getId());

        new GetCredits(castAdapter, crewAdapter).execute(movie.getId());

        new GetVideos(movie, handlingMovie).execute(movie.getId());

        new GetSimilarMovies(similarMoviesAdapter).execute(movie.getId());

        new GetReviews(reviewsList, reviewPage).execute(movie.getId());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTrailerTextColor(Movies m){
        if(!m.getTrailerUrl().equals("")) {
            TextView trailerText = findViewById(R.id.movie_trailer);
            trailerText.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void playTrailerThroughWebBrowser( View view){
        Uri webpage = Uri.parse(movie.getTrailerUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null && movie.getTrailerUrl()!=null) {
            startActivity(intent);
        }

    }

    public void getMoreReview(){
        new GetReviews(reviewsList, ++reviewPage, rotateLoading).execute(movie.getId());
    }

}
