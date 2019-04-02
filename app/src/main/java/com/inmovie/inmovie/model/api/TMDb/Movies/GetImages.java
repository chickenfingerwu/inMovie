package com.inmovie.inmovie.model.api.TMDb.Movies;

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

public class GetImages extends AsyncTask<Integer, Void, JSONObject> {
    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + integers[0] + "/images?api_key=" + BuildConfig.TMDb_API_key + "&language=en");
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
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
