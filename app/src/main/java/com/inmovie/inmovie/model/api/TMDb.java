package com.inmovie.inmovie.model.api;

import com.inmovie.inmovie.BuildConfig;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class TMDb {
    private String apiKey;
    private static TMDb instance = null;
    private String baseURL;
    private String[] backdrop_sizes;
    private String[] logo_sizes;
    private String[] poster_sizes;

    /**
     * Get TMDb instance.
     * @return reference to instance.
     */
    public static TMDb getInstance() {
        if (instance == null) {
            instance = new TMDb();
        }
        return instance;
    }

    /**
     * Initialize and assign API key
     */
    private TMDb() {
        this.apiKey = BuildConfig.TMDb_API_key;
        getConfiguration();
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

    /**
     * Get movie details
     * @param id TMDb ID
     * @return Movie details in JSON format
     */
    public JSONObject getDetails(int id) {
        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + BuildConfig.TMDb_API_key;
        JSONObject respondResult = query(url);

        if (respondResult == null) {
            return null;
        }

        return respondResult;
    }

    /**
     * Get credits
     * @param id TMDb ID
     * @return Cast details in JSON format
     */
    public JSONObject getCredits(int id) {
        String url = "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=" + BuildConfig.TMDb_API_key;
        JSONObject result = query(url);

        if (result == null) {
            return null;
        }

        return result;
    }

    /**
     * Get base URL, backdrop sizes.
     */
    public void getConfiguration() {
        String url = "https://api.themoviedb.org/3/configuration?api_key=" + BuildConfig.TMDb_API_key;
        JSONObject result = query(url);
        baseURL = (String) ((JSONObject) result.get("images")).get("secure_base_url");
        JSONArray backdrop = (JSONArray) ((JSONObject) result.get("images")).get("backdrop_sizes");
        JSONArray logo = (JSONArray) ((JSONObject) result.get("images")).get("logo_sizes");
        JSONArray poster = (JSONArray) ((JSONObject) result.get("images")).get("poster_sizes");
        backdrop_sizes = new String[backdrop.size()];
        logo_sizes = new String[logo.size()];
        poster_sizes = new String[poster.size()];
        for (int i = 0; i < backdrop_sizes.length; i++) {
            backdrop_sizes[i] = (String) backdrop.get(i);
        }
        for (int i = 0; i < logo_sizes.length; i++) {
            logo_sizes[i] = (String) logo.get(i);
        }
        for (int i = 0; i < poster_sizes.length; i++) {
            poster_sizes[i] = (String) poster.get(i);
        }
    }
}
