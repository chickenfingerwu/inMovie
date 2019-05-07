package com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.model.api.TMDb.TV.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.TV.GetDetails;

import java.util.ArrayList;

/**
 *This class implements the "Basic Info" tab from TvDetailsPagerAdapter,
 * it manages the data to be display on the tab "Basic Info" from the TvDetails activity,
 * data will be received from the TvDetails activity, also get additional data for adapters
 * using models from model.api
 */

public class TvBasicInfoFragment extends Fragment {

    //tv show id
    private int id;

    //adapters for crew list and cast list
    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;

    //RecyclerView for crew list and cast list
    private RecyclerView castList;
    private RecyclerView crewList;

    //layout managers manages how the item will be arranged
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager crewLayoutManager;

    private ScrollView scrollView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.tv_basic_info_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        //get id
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        Bundle args = getArguments();
        id = args.getInt("tv_id");
        scrollView = (ScrollView) view.findViewById(R.id.tv_basic_info_scrollview);

        //get views for layout
        TextView overview = (TextView) view.findViewById(R.id.tv_Description);

        ImageView banner = (ImageView) view.findViewById(R.id.tv_poster_banner);

        TextView numberRating = (TextView) view.findViewById(R.id.tv_numberRating);

        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.tv_ratingBar3);
        ratingBar.setNumStars(10);

        TextView additionalInfo = (TextView) view.findViewById(R.id.tv_additionalInfo);
        TextView genres_runtime = (TextView) view.findViewById(R.id.tv_genres_runtime);

        TextView releseDate = (TextView) view.findViewById(R.id.tv_release_date);

        //views list to send to Tv.GetDetails task
        ArrayList<View> views = new ArrayList<>();
        views.add(null);
        views.add(overview);
        views.add(banner);
        views.add(numberRating);
        views.add(ratingBar);
        views.add(additionalInfo);
        views.add(genres_runtime);
        views.add(releseDate);

        //make lists and adapters for cast and crew displays
        castAdapter = new CastListAdapters(view.getContext());
        crewAdapter = new CrewListAdapters(view.getContext());
        castList = (RecyclerView) view.findViewById(R.id.tv_cast_list);
        crewList = (RecyclerView) view.findViewById(R.id.tv_crew_list);
        castLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false);
        crewLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false);

        crewList.setHasFixedSize(true);
        castList.setHasFixedSize(true);
        castList.setLayoutManager(castLayoutManager);
        castList.setAdapter(castAdapter);
        crewList.setAdapter(crewAdapter);
        crewList.setLayoutManager(crewLayoutManager);

        //get data to populate views
        new GetDetails(views, null).execute(id);
        //get data to populate cast and crew lists
        new GetCredits(castAdapter, crewAdapter).execute(id);

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

}
