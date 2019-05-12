package com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.inmovie.inmovie.Adapters.TvEpisodeResultAdapter;
import com.inmovie.inmovie.DividerItemDecoration;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.model.api.TMDb.TV.Seasons.GetDetails;

import java.util.ArrayList;
import java.util.List;


/**
 *This class implements the "Seasons" tab from TvDetailsPagerAdapter,
 * it manages the data to be display on the tab "Seasons" from the TvDetails activity,
 * the data will be received from the TvDetails activity, also get additional data for adapters
 * using models from model.api
 */

public class TvSeasonFragment extends Fragment{

    //show's id
    private int id;

    //show's number of seasons
    private int numberOfSeasons;

    //seasons list
    private List<Integer> seasonsList;

    //adapter for spinner
    private ArrayAdapter<String> dataAdapter;

    //RecyclerView for episodes
    private RecyclerView episodeList;
    //layout manager to dictates how the episode will be arranged
    private RecyclerView.LayoutManager episodeLayoutManager;

    //adapters for episodes list
    private TvEpisodeResultAdapter episodeAdapter;

    //spinner to choose season
    private Spinner seasonSpinner;
    private Context c;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        c = getContext();
        return (View) inflater.inflate(R.layout.tv_season_info_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        //Get search token, page
        Bundle args = getArguments();
        id = args.getInt("tv_id");
        numberOfSeasons = args.getInt("tv_seasons");
        seasonsList = new ArrayList<>();

        //find Spinner in layout
        seasonSpinner = view.findViewById(R.id.season_spinner);

        //data for the Spinner
        ArrayList<String> dataForSpinner = new ArrayList<>();

        //set layout and data to the adapter
        dataAdapter = new ArrayAdapter<String>(c, R.layout.custom_spinner_item, dataForSpinner);
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        //Add seasons to seasonList
        if(numberOfSeasons > 0) {
            for (int i = 1; i <= numberOfSeasons; i++) {
                seasonsList.add(i);
                dataForSpinner.add("Season " + i);
            }
            //set adapter for spinner
            seasonSpinner.setAdapter(dataAdapter);
            seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    if(numberOfSeasons > 0) {
                        getEpisodes(position + 1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent){

                }
            });
        }
        else {
            dataForSpinner.add("No seasons yet");
        }

        episodeList = view.findViewById(R.id.tv_episodes_recyclerview);

        //we want the item to be arranged in a vertical manner
        episodeLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayout.VERTICAL, false);

        episodeAdapter = new TvEpisodeResultAdapter(view.getContext());
        episodeList.setHasFixedSize(true);
        episodeList.setAdapter(episodeAdapter);
        episodeList.setLayoutManager(episodeLayoutManager);
        episodeList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.white_divider)));

        //if there are at least 1 season then get the episodes
        if(numberOfSeasons > 0) {
            getEpisodes(1);
        }
    }

    //get episodes to populate list
    protected void getEpisodes(int ssNumber){
        Integer[] inputs = new Integer[2];
        inputs[0] = id;
        inputs[1] = ssNumber;
        new GetDetails(episodeAdapter, id).execute(inputs);
        episodeList.setAdapter(episodeAdapter);
    }
}
