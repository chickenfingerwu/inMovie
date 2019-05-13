package com.inmovie.inmovie.model.api.TMDb.TV;

import android.os.AsyncTask;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetImages extends AsyncTask<Integer, Void, JSONObject> {
    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/tv/" + integers[0] + "/images?api_key=" + BuildConfig.TMDb_API_key);

        try {
            JSONArray backdrop = result.getJSONArray("backdrops");
            for (int i = 0; i < backdrop.length(); i++) {
                JSONObject temp = backdrop.getJSONObject(i);
                String url = "https://image.tmdb.org/t/p/w500" + temp.getString("file_path");
                temp.remove("file_path");
                temp.put("file_path", url);
            }
            JSONArray poster = result.getJSONArray("posters");
            for (int i = 0; i < poster.length(); i++) {
                JSONObject temp = poster.getJSONObject(i);
                String url = "https://image.tmdb.org/t/p/w500" + temp.getString("file_path");
                temp.remove("file_path");
                temp.put("file_path", url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
