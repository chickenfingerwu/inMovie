package com.inmovie.inmovie.model.api.OMDb;

import android.os.AsyncTask;
import android.widget.TextView;

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

public class GetIMDbRatingTask extends AsyncTask<String, Void, String> {
    private TextView ratingView;

    public GetIMDbRatingTask(TextView ratingView) {
        this.ratingView = ratingView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = "";

        // strings[0]: IMDb id
        try {
            URL url = new URL("https://www.omdbapi.com/?apikey=" + BuildConfig.OMDb_API_key + "&i=" + strings[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }

            JSONObject topLevel = new JSONObject(builder.toString());
            res = topLevel.getString("imdbRating") + " (" + topLevel.getString("imdbVotes") + ")";
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        ratingView.setText(s);
    }
}
