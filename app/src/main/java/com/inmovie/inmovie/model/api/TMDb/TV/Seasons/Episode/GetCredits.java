package com.inmovie.inmovie.model.api.TMDb.TV.Seasons.Episode;

import android.os.AsyncTask;

import com.inmovie.inmovie.Actor;
import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.Crew;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetCredits extends AsyncTask<Integer, Void, JSONObject> {

    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;

    public GetCredits(CastListAdapters castAdapter, CrewListAdapters crewAdapter){
        this.castAdapter = castAdapter;
        this.crewAdapter = crewAdapter;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/tv/" + integers[0] + "/season/" + integers[1] + "/episode/" + integers[2] + "/credits?api_key=" + BuildConfig.TMDb_API_key);

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject){

        try{
            ArrayList<Actor> actorList = new ArrayList<>();
            JSONArray cast = jsonObject.getJSONArray("cast");
            JSONObject castObject = null;
            for(int i = 0; i < cast.length(); i++){
                castObject = cast.getJSONObject(i);
                String name = castObject.getString("name");
                String character = castObject.getString("character");
                String profile = castObject.getString("profile_path");

                Actor actor = new Actor(name, character, profile);
                actorList.add(actor);
            }
            castAdapter.setActor(actorList, true);


            ArrayList<Crew> crewList = new ArrayList<>();
            JSONArray crew = jsonObject.getJSONArray("crew");
            JSONObject crewObject = null;
            for(int i = 0; i < crew.length(); i++){
                crewObject = crew.getJSONObject(i);
                String name = crewObject.getString("name");
                String job = crewObject.getString("job");
                String profile = crewObject.getString("profile_path");

                Crew crew1 = new Crew(name, job, profile);
                crewList.add(crew1);
            }
            crewAdapter.setCrew(crewList, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
