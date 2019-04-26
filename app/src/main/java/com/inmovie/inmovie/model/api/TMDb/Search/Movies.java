package com.inmovie.inmovie.model.api.TMDb.Search;

import android.os.AsyncTask;

import com.inmovie.inmovie.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Movies extends AsyncTask<String, Void, JSONArray> {
    @Override
    protected JSONArray doInBackground(String... strings) {
        JSONArray result = new JSONArray();
        // strings[0]: name to search
        // strings[1]: page

        try {
            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDb_API_key + "&query=" + strings[0] + "&page=" + strings[1]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();

            String temp;
            while ((temp = reader.readLine()) != null) {
                response.append(temp);
            }

            JSONObject _temp = new JSONObject(response.toString());
            result = _temp.getJSONArray("results");
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
