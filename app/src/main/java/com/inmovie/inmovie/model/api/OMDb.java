package com.inmovie.inmovie.model.api;

import com.inmovie.inmovie.BuildConfig;

import org.json.simple.JSONObject;

import java.util.concurrent.ExecutionException;

public class OMDb {
    private static OMDb instance;
    private String apiKey;
    private String baseURL;

    public static OMDb getInstance() {
        if (instance == null) {
            instance = new OMDb();
        }
        return instance;
    }

    /**
     * Initialize OMDb API
     */
    private OMDb() {
        apiKey = BuildConfig.OMDb_API_key;
        baseURL = "https://www.omdbapi.com/?apikey=" + apiKey + "&";
    }

    /**
     * Query data from an URL
     * @param url URL to get data
     * @return JSON data from server
     */
    private JSONObject query(String url) {
        JSONObject result;

        String res = "";

        try {
            res = new OkHttp3Get().execute(url).get();
            System.out.println(res);
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
     * Get rating from IMDb
     * @param imdbId IMDb ID. Example: "tt2758770"
     * @return rating
     */
    public double getIMDbRating(String imdbId) {
        double rating = 0;

        JSONObject data = query(baseURL + "i=" + imdbId);
        rating = Double.valueOf((String) data.get("imdbRating"));

        return rating;
    }

    public String getDirector(String imdbId) {
        String director = "";

        JSONObject data = query(baseURL + "i=" + imdbId);
        director = (String) data.get("Director");

        return director;
    }
}
