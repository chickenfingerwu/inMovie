package com.inmovie.inmovie.model.api;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OMDb {
    private String apiKey;
    private String baseURL;

    /**
     * Initialize OMDb API
     * @param key API key
     */
    public OMDb(String key) {
        apiKey = key;
        baseURL = "http://www.omdbapi.com/?apikey=" + apiKey + "&";
    }

    /**
     * Query data from an URL
     * @param url URL to get data
     * @return JSON data from server
     */
    private JSONObject query(URL url) {
        HttpURLConnection connection = null;
        JSONObject jsonObject = null;

        try {
            // Initialize connection and set its properties
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set method to GET
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Get and parse response to JSON
            jsonObject = JSON.parseStringToJSON(TheTVDB.getResponse(connection));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect(); // Disconnect connection
            }
        }
        return jsonObject;
    }

    /**
     * Get rating from IMDb
     * @param imdbId IMDb ID. Example: "tt2758770"
     * @return rating
     */
    public double getIMDbRating(String imdbId) {
        double rating = 0;

        try {
            JSONObject data = query(new URL(baseURL + "i=" + imdbId));
            rating = Double.valueOf((String) data.get("imdbRating"));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return rating;
    }
}
