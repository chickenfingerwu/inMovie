package com.inmovie.inmovie.model.api.TMDb.TV.Seasons.Episode;

import android.os.AsyncTask;

import com.inmovie.inmovie.Actor;
import com.inmovie.inmovie.Adapters.CastListAdapters;
import com.inmovie.inmovie.Adapters.CrewListAdapters;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.Crew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class GetCredits extends AsyncTask<Integer, Void, JSONObject> {

    private CastListAdapters castAdapter;
    private CrewListAdapters crewAdapter;

    public GetCredits(CastListAdapters castAdapter, CrewListAdapters crewAdapter){
        this.castAdapter = castAdapter;
        this.crewAdapter = crewAdapter;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();

        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "/season/" + integers[1] + "/episode/" + integers[2] + "/credits?api_key=" + BuildConfig.TMDb_API_key);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }

            result = new JSONObject(builder.toString());
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

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
