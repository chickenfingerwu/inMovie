package com.inmovie.inmovie.model.api;

import com.inmovie.inmovie.BuildConfig;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TMDb {
    private String apiKey;

    /**
     * Initialize and assign API key
     */
    public TMDb() {
        this.apiKey = BuildConfig.TMDb_API_key;
    }

    /**
     * Return movies list based on name provided
     * @param name
     * @return List of movies in JSONObject
     */
    public JSONObject searchMoviesByName(String name) {
        JSONObject result = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/search/movie?include_adult=false&page=1&query=" + URLEncoder.encode(name, "UTF-8") + "&language=en-US&api_key=" + apiKey)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            result = JSON.parseStringToJSON(response.body().string());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
