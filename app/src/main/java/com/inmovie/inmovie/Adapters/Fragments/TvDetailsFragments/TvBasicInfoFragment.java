package com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.Handlers.HandlingTvRating;
import com.inmovie.inmovie.Handlers.HandlingTvShow;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.Decorations.SideSpaceItemDecoration;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;
import com.inmovie.inmovie.model.api.TMDb.TV.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.TV.GetDetails;
import com.inmovie.inmovie.model.api.TMDb.TV.GetReviews;
import com.inmovie.inmovie.model.api.TMDb.TV.GetSimilarTVShows;
import com.inmovie.inmovie.model.api.TMDb.TV.GetVideos;
import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;

/**
 *This class implements the "Basic Info" tab from TvDetailsPagerAdapter,
 * it manages the data to be display on the tab "Basic Info" from the TvDetails activity,
 * data will be received from the TvDetails activity, also get additional data for adapters
 * using models from model.api
 */

public class TvBasicInfoFragment extends Fragment {

    //tv show id
    private int id;

    private View root;

    private ProgressBar rotateLoading;

    private HandlingTvShow handler;
    private HandlingTvRating ratingHandler;

    //adapters for crew list and cast list
    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;
    private TrendingsAdapter similarShowAdapter;

    //RecyclerView for crew list and cast list
    private RecyclerView castList;
    private RecyclerView crewList;
    private RecyclerView similarShow;
    private LinearLayout reviewsList;
    private int reviewPage = 1;

    //layout managers manages how the item will be arranged
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager crewLayoutManager;
    private RecyclerView.LayoutManager similarShowManager;

    private ScrollView scrollView;
    private TvShow tvShow;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.tv_basic_info_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        handler = new HandlingTvShow(this);
        ratingHandler = new HandlingTvRating(this);
        root = view;
        //get id
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        Bundle args = getArguments();
        id = args.getInt("tv_id");
        tvShow = (TvShow) args.getSerializable("tvShow");
        scrollView = (ScrollView) view.findViewById(R.id.tv_basic_info_scrollview);

        //get views for layout

        //Expandable plot view
        ExpandableTextView overview = (ExpandableTextView) view.findViewById(R.id.tv_Description);
        //max line is 4
        overview.setMaxLines(4);

        TextView trailer = view.findViewById(R.id.tv_trailer);
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTrailerThroughWebBrowser(v);
            }
        });

        //toggle button to see more of plot
        TextView toggle = view.findViewById(R.id.tv_button_toggle);
        toggle.setText(R.string.expand);

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

        TextView seeMoreReviews = (TextView) view.findViewById(R.id.loadMoreButton);
        rotateLoading = (ProgressBar) view.findViewById(R.id.rotateLoading);
        seeMoreReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateLoading.setVisibility(View.VISIBLE);
                getMoreReview();
            }

        });

        reviewsList = (LinearLayout) view.findViewById(R.id.tv_reviews);

        ImageView banner = (ImageView) view.findViewById(R.id.tv_poster_banner);
        ImageView poster = (ImageView) view.findViewById(R.id.tv_poster);

        TextView additionalInfo = (TextView) view.findViewById(R.id.tv_additionalInfo);
        TextView genres = (TextView) view.findViewById(R.id.genres);

        TextView releseDate = (TextView) view.findViewById(R.id.air_date);

        TextView director = (TextView) view.findViewById(R.id.tv_director);
        TextView releaseYear = (TextView) view.findViewById(R.id.tv_releaseYear);
        TextView runtime = (TextView) view.findViewById(R.id.tv_runtime);

        //views list to send to Tv.GetDetails task
        ArrayList<View> views = new ArrayList<>();
        views.add(director);
        views.add(releaseYear);
        views.add(runtime);
        views.add(poster);
        views.add(overview);
        views.add(banner);
        views.add(additionalInfo);
        views.add(genres);
        views.add(releseDate);

        //make lists and adapters for cast and crew displays
        castAdapter = new CastListAdapters(view.getContext());
        crewAdapter = new CrewListAdapters(view.getContext());
        similarShowAdapter = new TrendingsAdapter(view.getContext());
        castList = (RecyclerView) view.findViewById(R.id.tv_cast_list);
        crewList = (RecyclerView) view.findViewById(R.id.tv_crew_list);
        similarShow = (RecyclerView) view.findViewById(R.id.suggest_tv_list);
        similarShowManager = new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false);
        castLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false);
        crewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false);

        similarShow.setHasFixedSize(true);
        similarShow.setLayoutManager(similarShowManager);
        similarShow.setAdapter(similarShowAdapter);
        crewList.setHasFixedSize(true);
        castList.setHasFixedSize(true);
        castList.setLayoutManager(castLayoutManager);
        castList.setAdapter(castAdapter);
        crewList.setAdapter(crewAdapter);
        crewList.setLayoutManager(crewLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing2);
        similarShow.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels));

        int spacingInPixels1 = getResources().getDimensionPixelSize(R.dimen.spacingCast);
        castList.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels1));
        crewList.addItemDecoration(new SideSpaceItemDecoration(spacingInPixels1));
        //get data to populate views
        new GetDetails(views, tvShow, ratingHandler, handler).execute(id);
        //get data to populate cast and crew lists
        new GetCredits(castAdapter, crewAdapter).execute(id);
        //get data to populate similar show list
        new GetSimilarTVShows(similarShowAdapter).execute(id);
        //get link to trailer
        new GetVideos(tvShow, handler).execute(id);
        //get reviews
        new GetReviews(reviewsList, reviewPage).execute(id);
        //set scroll position to top
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });
    }


    public void setCastList(RecyclerView castList) {
        this.castList = castList;
    }

    public void setCrewAdapter(CrewListAdapters crewAdapter) {
        this.crewAdapter = crewAdapter;
    }

    public void setCrewLayoutManager(RecyclerView.LayoutManager crewLayoutManager) {
        this.crewLayoutManager = crewLayoutManager;
    }

    public void setCrewList(RecyclerView crewList) {
        this.crewList = crewList;
    }

    public void getMoreReview(){
        new com.inmovie.inmovie.model.api.TMDb.TV.GetReviews(reviewsList, ++reviewPage, rotateLoading).execute(tvShow.getId());
    }

    public void setTrailerTextColor(TvShow show){
        TextView trailerText = root.findViewById(R.id.tv_trailer);
        if(!show.getTrailerUrl().equals("")) {
            System.out.println("have trailer");
            trailerText.setTextColor(getResources().getColor(R.color.white));
        }
        else {
            System.out.println("no trailer");
            trailerText.setTextColor(getResources().getColor(R.color.grey));
        }
    }

    public void playTrailerThroughWebBrowser(View view){
        Uri webpage = Uri.parse(tvShow.getTrailerUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null && tvShow.getTrailerUrl()!= null) {
            startActivity(intent);
        }

    }

    public void setRatingSection(Bundle ratingData){
        ConstraintLayout outerImdbRating = root.findViewById(R.id.imdb_rating);
        ProgressBar imdbRoundRating = (ProgressBar) outerImdbRating.findViewById(R.id.stats_progressbar);
        ConstraintLayout outerTmdbRating = root.findViewById(R.id.tmdb_rating);
        ProgressBar tmdbRoundRating = (ProgressBar) outerTmdbRating.findViewById(R.id.stats_progressbar);

        TextView imdbText = root.findViewById(R.id.imdbText);
        TextView tmdbText = root.findViewById(R.id.tmdbText);
        TextView imdbScoreText = (TextView) outerImdbRating.findViewById(R.id.imdb_score_center_round);
        TextView tmdbScoreText = (TextView) outerTmdbRating.findViewById(R.id.tmdb_score_center_round);

        if(ratingData!=null){
            Double imdb_score = ratingData.getDouble("imdbRating");
            String imdb_votes = ratingData.getString("imdbVotes");
            Double tmdb_score = ratingData.getDouble("tmdbRating");
            Integer tmdb_votes = ratingData.getInt("tmdbVotes");

            imdbRoundRating.setProgress(imdb_score.intValue());
            tmdbRoundRating.setProgress(tmdb_score.intValue());
            imdbText.append(" (" + imdb_votes + " votes)");
            tmdbText.append(" (" + tmdb_votes + " votes)");

            imdbScoreText.setText(imdb_score + "/10");
            tmdbScoreText.setText(tmdb_score + "/10");

        }

    }
}
