package com.inmovie.inmovie.model.api.OMDb;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class GetRating {
    public static JSONObject getRatingByID(String imdb) {
        JSONObject res = new JSONObject();

        try {
            JSONObject topLevel = Network.getJSONObject("https://www.omdbapi.com/?apikey=" + BuildConfig.OMDb_API_key + "&i=" + imdb);
            if(!topLevel.getString("imdbRating").equals("N/A")) {
                res.put("score", Double.parseDouble(topLevel.getString("imdbRating")));
                String votes = topLevel.getString("imdbVotes");
                res.put("votes", votes);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }
}