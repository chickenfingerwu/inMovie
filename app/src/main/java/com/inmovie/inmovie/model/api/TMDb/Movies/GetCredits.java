package com.inmovie.inmovie.model.api.TMDb.Movies;

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

    private CastListAdapters castListAdapters = null;
    private CrewListAdapters crewListAdapters = null;

    public GetCredits(CastListAdapters castListAdapters, CrewListAdapters crewListAdapters){
        this.castListAdapters = castListAdapters;
        this.crewListAdapters = crewListAdapters;
    }

    public GetCredits() {}

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + integers[0] + "/credits?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US");
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
    protected void onPostExecute(JSONObject jsonObject) {
        if (crewListAdapters != null && castListAdapters != null) {
            try {
                ArrayList<Actor> actors = new ArrayList<>();
                ArrayList<Crew> crews = new ArrayList<>();
                JSONArray castJSON = new JSONArray();
                JSONArray crewJSON = new JSONArray();
                if (castListAdapters != null) {
                    castJSON = jsonObject.getJSONArray("cast");
                    String name = "";
                    String role = "";
                    String profile = "";
                    for (int i = 0; i < castJSON.length(); i++) {
                        Actor a;
                        JSONObject actor = castJSON.getJSONObject(i);
                        name = actor.getString("name");
                        role = actor.getString("character");
                        profile = actor.getString("profile_path");
                        a = new Actor(name, role, profile);
                        actors.add(a);
                    }
                    castListAdapters.setActor(actors, false);
                }
                if (crewListAdapters != null) {
                    crewJSON = jsonObject.getJSONArray("crew");
                    String name = "";
                    String role = "";
                    String profile = "";
                    for (int i = 0; i < crewJSON.length(); i++) {
                        Crew c;
                        JSONObject crew = crewJSON.getJSONObject(i);
                        name = crew.getString("name");
                        role = crew.getString("job");
                        profile = crew.getString("profile_path");
                        c = new Crew(name, role, profile);
                        crews.add(c);
                    }
                    crewListAdapters.setCrew(crews, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
