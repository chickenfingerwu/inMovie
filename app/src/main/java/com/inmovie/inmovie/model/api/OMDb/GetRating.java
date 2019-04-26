package com.inmovie.inmovie.model.api.OMDb;

import com.inmovie.inmovie.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetRating {
    public static JSONObject getRatingByID(String imdb) {
        JSONObject res = new JSONObject();

        try {
            URL url = new URL("https://www.omdbapi.com/?apikey=" + BuildConfig.OMDb_API_key + "&i=" + imdb);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }

            JSONObject topLevel = new JSONObject(builder.toString());
            if(!topLevel.getString("imdbRating").equals("N/A")) {
                res.put("score", Double.parseDouble(topLevel.getString("imdbRating")));
                String votes = topLevel.getString("imdbVotes");
                res.put("votes", votes);
            }
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static JSONObject getRatingByName(String name) {
        JSONObject res = new JSONObject();

        try {
            URL url = new URL("https://www.omdbapi.com/?apikey=" + BuildConfig.OMDb_API_key + "&t=" + name);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }

            JSONObject topLevel = new JSONObject(builder.toString());
            if(!topLevel.getString("imdbRating").equals("N/A")) {
                res.put("score", Double.parseDouble(topLevel.getString("imdbRating")));
                String votes = topLevel.getString("imdbVotes");
                res.put("votes", votes);
            }
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}