package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.os.AsyncTask;

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

public class GetSimilarMovies extends AsyncTask<Integer, Void, JSONObject> {
    private int page;

    public GetSimilarMovies() {
        page = 1;
    }

    public GetSimilarMovies(int page) {
        this.page = page;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();

        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + integers[0] + "/similar?api_key=" + BuildConfig.TMDb_API_key  + "&page=" + page);
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
}
