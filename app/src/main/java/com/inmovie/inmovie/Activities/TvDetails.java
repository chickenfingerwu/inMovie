package com.inmovie.inmovie.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.TvDetailsPagerAdapter;
import com.inmovie.inmovie.HandlingTvShow;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.ScrollingDisableViewPager;
import com.inmovie.inmovie.TVclasses.TvShow;
import com.inmovie.inmovie.model.api.TMDb.TV.GetDetails;
import com.squareup.picasso.Picasso;


public class TvDetails extends AppCompatActivity {
    private TvShow tvShow;
    private ImageView banner;
    private TextView title;
    private TextView additionalInfo;
    private HandlingTvShow handler;

    private TabLayout.OnTabSelectedListener listener;
    private TvDetailsPagerAdapter tvDetailsPagerAdapter;
    private ScrollingDisableViewPager viewPager;
    private TabLayout.TabLayoutOnPageChangeListener pagerListener;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handler = new HandlingTvShow(this);
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
            additionalInfo.setText("Genres: " + show.getGenres() + " | Runtime: " + show.getRuntime() + " minutes");
        }
    }

    public void setTabs(TvShow show){
        tvDetailsPagerAdapter = new TvDetailsPagerAdapter(getSupportFragmentManager(), show.getId(), show.getNumbofSeason(), 2);
        viewPager = (ScrollingDisableViewPager) findViewById(R.id.tv_info_pager);
        viewPager.setAdapter(tvDetailsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tv_info_tabs_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Basic Info");
        tabLayout.getTabAt(1).setText("Seasons");

        listener = new TabLayout.OnTabSelectedListener() {
            private ScrollView v;
            private int scrollX = 0;
            private int scrollY = 0;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Basic Info")){
                    v.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            v.scrollTo(scrollX, scrollY);
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getText().equals("Basic Info")){
                    v = (ScrollView) findViewById(R.id.tv_basic_info_scrollview);
                    scrollX = v.getScrollX();
                    scrollY = v.getScrollY();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        };
        tabLayout.addOnTabSelectedListener(listener);
    }
}