package com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments;

import android.graphics.Color;
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
import com.inmovie.inmovie.TVclasses.TvShow;
import com.inmovie.inmovie.model.api.TMDb.TV.GetCredits;
import com.inmovie.inmovie.model.api.TMDb.TV.GetDetails;

import java.util.ArrayList;

public class TvBasicInfoFragment extends Fragment {
    private int id;
    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;
    private RecyclerView castList;
    private RecyclerView crewList;
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
        Bundle args = getArguments();
        id = args.getInt("tv_id");

        //create views for layout
        TextView overview = view.findViewById(R.id.tv_Description);

        ImageView banner = view.findViewById(R.id.tv_poster_banner);

        TextView numberRating = view.findViewById(R.id.tv_numberRating);

        RatingBar ratingBar = view.findViewById(R.id.tv_ratingBar3);
        ratingBar.setNumStars(10);

        TextView additionalInfo = (TextView) view.findViewById(R.id.tv_additionalInfo);
        TextView genres_runtime = (TextView) view.findViewById(R.id.tv_genres_runtime);

        TextView releseDate = (TextView) view.findViewById(R.id.tv_release_date);

        ArrayList<View> views = new ArrayList<>();
        views.add(null);
        views.add(overview);
        views.add(banner);
        views.add(numberRating);
        views.add(ratingBar);
        views.add(additionalInfo);
        views.add(genres_runtime);
        views.add(releseDate);


        //get datas to populate views
        new GetDetails(views, null).execute(id);

        scrollView = (ScrollView) view.findViewById(R.id.tv_basic_info_scrollview);

        System.out.println(scrollView.getY());


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

        //get datas to populate cast and crew lists
        new GetCredits(castAdapter, crewAdapter).execute(id);
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
