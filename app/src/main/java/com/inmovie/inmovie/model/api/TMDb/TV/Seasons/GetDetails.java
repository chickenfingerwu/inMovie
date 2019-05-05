package com.inmovie.inmovie.model.api.TMDb.TV.Seasons;

import android.os.AsyncTask;
import android.view.View;

import com.inmovie.inmovie.Adapters.TvEpisodeResultAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.TVclasses.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class GetDetails extends AsyncTask<Integer, Void, JSONObject> {
    /**
     * Get details about a season of TV series
     * @param integers TV ID and season number
     * @return Details
     */
    private TvEpisodeResultAdapter episodeResultAdapter;

    public GetDetails(TvEpisodeResultAdapter a){
        episodeResultAdapter = a;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();

        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "/season/" + integers[1] + "?api_key=" + BuildConfig.TMDb_API_key);
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
    public void onPostExecute(JSONObject jsonObject){
        try {
            ArrayList<Episode> episodes = new ArrayList<>();
            JSONArray seasonEp = jsonObject.getJSONArray("episodes");
            for (int i = 0; i < seasonEp.length(); i++){
                JSONObject ep = seasonEp.getJSONObject(i);
                String thumbnail = ep.getString("still_path");
                String name = ep.getString("name");

                Episode e = new Episode();
                e.setTitle(name);
                e.setPoster(thumbnail);
                episodes.add(e);
            }
            episodeResultAdapter.setEpisodesList(episodes, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
