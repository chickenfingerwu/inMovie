package com.inmovie.inmovie.model.api;

import com.inmovie.inmovie.BuildConfig;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class TMDb {
    private String apiKey;

    /**
     * Initialize and assign API key
     */
    public TMDb() {
        this.apiKey = BuildConfig.TMDb_API_key;
    }

    private JSONObject query(String url) {
        JSONObject result;

        String res = "";

        try {
            res = new OkHttp3Get().execute(url).get();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (res.equals("")) {
            return null;
        }

        result = JSON.parseStringToJSON(res);

        return result;
    }

    /**
     * Return movies list based on name provided
     * @param name Movie name
     * @return List of movies in JSONObject
     */
    public JSONArray searchMoviesByName(String name) {
        String url = null;
        try {
            url = "https://api.themoviedb.org/3/search/movie?query=" + URLEncoder.encode(name, "UTF-8") + "&api_key=" + apiKey;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject respondResult = query(url);

        if (respondResult == null) { // In case something goes wrong, return null
            return null;
        }

        JSONArray result = (JSONArray) respondResult.get("results");

        // Check for additional page
        long total_pages = (long) respondResult.get("total_pages");
        for (int page = 2; page <= total_pages; page++) {
            try {
                url = "https://api.themoviedb.org/3/search/movie?query=" + URLEncoder.encode(name, "UTF-8") + "&page=2&api_key=" + apiKey;
                respondResult = query(url);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            JSONArray temp_result = (JSONArray) respondResult.get("results");
            for (int i = 0; i < temp_result.size(); i++) {
                result.add(temp_result.get(i));
            }
        }

        return result;
    }
}
