package com.inmovie.inmovie.Activities.MoviesActivities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetDetails;


import java.util.ArrayList;



/* This class implements the Movie's details activity,
user will see this when choosing a movie
 */

public class MovieDetails extends AppCompatActivity {

    //Adapters to display data of cast and crew
    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;

    //RecyclerView is where Adapters will display data onto
    private RecyclerView castList;
    private RecyclerView crewList;

    //LayoutManager to determine the layout of the RecyclerView
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager crewLayoutManager;

    //this Movie instance holds the selected movie data
    private Movies movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get Intent that started this activity and extract selected data (a movie in this case) from previous activity
        Intent intent = getIntent();
        movie = (Movies) intent.getSerializableExtra("serialize_data");


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

        TextView overview = findViewById(R.id.movieDescription);

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
        //float numRating = Float.parseFloat(numberRating.getText().toString());
        //ratingBar.setRating(numRating);
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
}
