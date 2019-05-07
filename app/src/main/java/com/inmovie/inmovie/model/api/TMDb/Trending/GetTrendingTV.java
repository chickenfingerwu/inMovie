package com.inmovie.inmovie.model.api.TMDb.Trending;

import android.os.AsyncTask;
import android.os.Message;

import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.HandlingTrending;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.TVclasses.TvShow;

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

import javax.net.ssl.HttpsURLConnection;

public class GetTrendingTV extends AsyncTask<Void, Void, JSONObject> {

    private TrendingsAdapter adapter;

    public GetTrendingTV(TrendingsAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/trending/tv/week?api_key=" + BuildConfig.TMDb_API_key);
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
    protected void onPostExecute(JSONObject jsonObject){
        try {
            ArrayList<Movies> showsArrayList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject movieJSON = null;
            for(int i = 0; i < jsonArray.length(); i++){
                movieJSON = jsonArray.getJSONObject(i);
                TvShow show = new TvShow();
                String poster_url = movieJSON.getString("poster_path");
                String name = movieJSON.getString("name");
                String releaseDate = movieJSON.getString("first_air_date");
                int id = movieJSON.getInt("id");
                show.setId(id);
                show.setPoster(poster_url);
                show.setTitle(name);
                show.setReleaseDate(releaseDate);
                showsArrayList.add(show);
            }
            adapter.setMoviesList(showsArrayList, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
