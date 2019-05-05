package com.inmovie.inmovie.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.Adapters.ResultPagerAdapter;
import com.inmovie.inmovie.Adapters.TvDetailsPagerAdapter;
import com.inmovie.inmovie.HandlingBanner;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.TVclasses.TvShow;
import com.inmovie.inmovie.model.api.TMDb.TV.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.TV.GetDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class TvDetails extends AppCompatActivity {
    private TvShow tvShow;
    ImageView banner;
    TextView title;
    TextView additionalInfo;
    HandlingBanner handler;

    private TvDetailsPagerAdapter tvDetailsPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handler = new HandlingBanner(this);
        //Get Intent that started this activity and extract string
        Intent intent = getIntent();
        tvShow = (TvShow) intent.getSerializableExtra("tvShow");

        banner = (ImageView) findViewById(R.id.tv_poster_banner);

        title = (TextView) findViewById(R.id.tv_name);
        title.setTextColor(Color.WHITE);

        additionalInfo = (TextView) findViewById(R.id.tv_additionalInfo);

        try {
            new GetDetails(tvShow, handler).execute(tvShow.getId());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        tvDetailsPagerAdapter = new TvDetailsPagerAdapter(getSupportFragmentManager(), tvShow.getId(), tvShow.getNumbofSeason(), 2);
        viewPager = (ViewPager) findViewById(R.id.tv_info_pager);
        viewPager.setAdapter(tvDetailsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tv_info_tabs_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Basic Info");
        tabLayout.getTabAt(1).setText("Seasons");
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

    public void setShowBanner(TvShow show){
        if(show != null){
            Picasso.with(this)
                    .load(show.getBackdrop())
                    .placeholder(R.color.grey)
                    .into(banner);
            title.setText(show.getTitle());
            additionalInfo.setText("Genres: " + show.getGenres() + " | Runtime: " + show.getRuntime());
        }
    }
}