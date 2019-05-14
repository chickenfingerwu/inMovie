package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.os.AsyncTask;
import android.widget.TextView;

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

    private TextView director = null;

    private CastListAdapters castListAdapters = null;
    private CrewListAdapters crewListAdapters = null;

    public GetCredits(CastListAdapters castListAdapters, CrewListAdapters crewListAdapters, TextView director){
        this.castListAdapters = castListAdapters;
        this.crewListAdapters = crewListAdapters;
        this.director = director;
    }

    public GetCredits(){}


    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/movie/" + integers[0] + "/credits?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US");

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

                        if(director!=null){
                            if(role.equals("Director")){
                                if(director.getText().equals("")) {
                                    director.setText(name);
                                }
                                else {
                                    director.append(", " + name);
                                }
                            }
                        }

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
