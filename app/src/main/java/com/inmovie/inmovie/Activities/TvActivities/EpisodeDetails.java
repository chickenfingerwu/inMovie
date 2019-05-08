package com.inmovie.inmovie.Activities.TvActivities;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.HandlingEpisode;
import com.inmovie.inmovie.HandlingMovie;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.TVclasses.Episode;
import com.inmovie.inmovie.model.api.TMDb.Movies.GetVideos;
import com.inmovie.inmovie.model.api.TMDb.TV.Seasons.Episode.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.TV.Seasons.Episode.GetDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;

public class EpisodeDetails extends AppCompatActivity {
    private Episode episode;

    private HandlingEpisode handlingEpisode;

    //Adapters to display data of cast and crew
    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;

    //RecyclerView is where Adapters will display data onto
    private RecyclerView castList;
    private RecyclerView crewList;

    //LayoutManager to determine the layout of the RecyclerView
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager crewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get Intent that started this activity and extract selected data (an episode in this case) from previous activity
        Intent intent = getIntent();
        episode = (Episode) intent.getSerializableExtra("serialize_data");
        handlingEpisode = new HandlingEpisode(this);

        castList = (RecyclerView) findViewById(R.id.episode_cast_list);
        //Create a horizontal layout for the RecyclerView
        castLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        castAdapter = new CastListAdapters(this);

        castList.setHasFixedSize(true);
        castList.setLayoutManager(castLayoutManager);
        castList.setAdapter(castAdapter);

        crewList = (RecyclerView) findViewById(R.id.episode_crew_list);
        crewLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        crewAdapter = new CrewListAdapters(this);

        crewList.setHasFixedSize(true);
        crewList.setAdapter(crewAdapter);
        crewList.setLayoutManager(crewLayoutManager);

        TextView toggle = findViewById(R.id.episode_button_toggle);
        toggle.setText(R.string.expand);

        ExpandableTextView overview = findViewById(R.id.episode_Description);
        overview.setInterpolator(new OvershootInterpolator());
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle.setText(overview.isExpanded() ? R.string.expand : R.string.collapse);
                overview.toggle();
            }
        });

        ImageView banner = findViewById(R.id.episode_poster_banner);

        TextView contentName = findViewById(R.id.episode_name);

        TextView episodeSeason = findViewById(R.id.episode_season);

        TextView releseDate = (TextView) findViewById(R.id.episode_release_date);

        ArrayList<View> views = new ArrayList<>();
        views.add(contentName);
        views.add(episodeSeason);
        views.add(overview);
        views.add(banner);
        views.add(releseDate);

        contentName.setTextColor(Color.WHITE);


        Integer[] inputs = new Integer[3];
        inputs[0] = episode.getTvID();
        inputs[1] = episode.getSeasonNumber();
        inputs[2] = episode.getEpisodeNumber();

        new GetDetails(views, episode, handlingEpisode).execute(inputs);
        new GetCredits(castAdapter, crewAdapter).execute(inputs);
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

    public void setEpisodeBanner(Episode e){
        if(e != null) {
            ImageView banner = (ImageView) findViewById(R.id.episode_poster_banner);
            Picasso.with(this)
                    .load(e.getBackdrop())
                    .placeholder(R.color.grey)
                    .into(banner);
        }
    }

    public void setTrailerTextColor(Movies m){
        if(!m.getTrailerUrl().equals("")) {
            TextView trailerText = findViewById(R.id.movie_trailer);
            trailerText.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void playTrailerThroughWebBrowser( View view){
        Uri webpage = Uri.parse(episode.getTrailerUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null && episode.getTrailerUrl()!=null) {
            startActivity(intent);
        }

    }

}
