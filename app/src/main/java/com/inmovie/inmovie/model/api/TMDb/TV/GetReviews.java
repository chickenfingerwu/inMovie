package com.inmovie.inmovie.model.api.TMDb.TV;

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

public class GetReviews extends AsyncTask<Integer, Void, JSONObject> {
    public GetReviews() {}

    // TODO: Create another constructor with Views

    /**
     * Get reviews
     * @param integers Movie's ID and page
     * @return JSONObject
     */
    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();

        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "/reviews?api_key=" + BuildConfig.TMDb_API_key  + "&page=" + integers[1]);
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

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        // TODO: Set something here
    }
}
