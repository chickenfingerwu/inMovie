package com.inmovie.inmovie.model.api.TMDb.TV;

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

public class GetVideos extends AsyncTask<Integer, Void, JSONArray> {
    @Override
    protected JSONArray doInBackground(Integer... integers) {
        JSONArray result = new JSONArray();

        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "/videos?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }

            result = new JSONObject(builder.toString()).getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                if (!result.getJSONObject(i).getString("site").equalsIgnoreCase("YouTube") ||
                    !(result.getJSONObject(i).getString("type").equalsIgnoreCase("Trailer") || result.getJSONObject(i).getString("type").equalsIgnoreCase("Teaser"))) {
                    result.remove(i--);
                }
                else {
                    JSONObject temp = result.getJSONObject(i);
                    String youtube_url = "https://www.youtube.com/embed/" + temp.getString("key");
                    temp.remove("key");
                    temp.put("link", youtube_url);
                }
            }
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
