package com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.inmovie.inmovie.Adapters.TvEpisodeResultAdapter;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.model.api.TMDb.TV.Seasons.GetDetails;

public class TvSeasonFragment extends Fragment {
    private int id;
    private int seasonNumber;
    private int numberOfSeasons;
    private RecyclerView episodeList;
    private TvEpisodeResultAdapter episodeAdapter;
    private Spinner seasonSpinner;
    private RecyclerView.LayoutManager episodeLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return (View) inflater.inflate(R.layout.tv_season_info_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //Get search token, page
        Bundle args = getArguments();
        id = args.getInt("tv_id");
        numberOfSeasons = args.getInt("tv_seasons");
        seasonNumber = 1;

        seasonSpinner = view.findViewById(R.id.season_spinner);

        episodeList = view.findViewById(R.id.tv_episodes_recyclerview);
        episodeLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayout.VERTICAL, false);

        episodeAdapter = new TvEpisodeResultAdapter(view.getContext());
        episodeList.setHasFixedSize(true);
        episodeList.setAdapter(episodeAdapter);
        episodeList.setLayoutManager(episodeLayoutManager);

        getEpisodes(1);
    }

    protected void getEpisodes(int ssNumber){
        Integer[] inputs = new Integer[2];
        inputs[0] = id;
        inputs[1] = ssNumber;
        new GetDetails(episodeAdapter).execute(inputs);
    }
}
