package com.inmovie.inmovie.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.TVclasses.TvShow;
import com.inmovie.inmovie.model.api.TMDb.TV.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.TV.GetDetails;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class TvDetails extends AppCompatActivity {
    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;
    private RecyclerView castList;
    private RecyclerView crewList;
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager crewLayoutManager;
    private TvShow tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        castList = (RecyclerView) findViewById(R.id.tv_cast_list);
        castLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        castAdapter = new CastListAdapters(this);

        castList.setHasFixedSize(true);
        castList.setLayoutManager(castLayoutManager);
        castList.setAdapter(castAdapter);

        crewList = (RecyclerView) findViewById(R.id.tv_crew_list);
        crewLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        crewAdapter = new CrewListAdapters(this);

        crewList.setHasFixedSize(true);
        crewList.setAdapter(crewAdapter);
        crewList.setLayoutManager(crewLayoutManager);
        //Get Intent that started this activity and extract string
        Intent intent = getIntent();
        tvShow = (TvShow) intent.getSerializableExtra("serialize_data");

        TextView overview = findViewById(R.id.tv_Description);

        ImageView banner = findViewById(R.id.tv_poster_banner);

        TextView contentName = findViewById(R.id.tv_name);

        TextView numberRating = findViewById(R.id.tv_numberRating);

        RatingBar ratingBar = findViewById(R.id.tv_ratingBar3);
        ratingBar.setNumStars(10);

        TextView additionalInfo = (TextView) findViewById(R.id.tv_additionalInfo);
        TextView genres_runtime = (TextView) findViewById(R.id.tv_genres_runtime);

        TextView releseDate = (TextView) findViewById(R.id.tv_release_date);

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

        new GetDetails(views).execute(tvShow.getId());

        new GetCredits(castAdapter, crewAdapter).execute(tvShow.getId());
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