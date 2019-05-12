package com.inmovie.inmovie.model.api.TMDb.TV;

import android.os.AsyncTask;

import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.BuildConfig;
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

public class GetSimilarTVShows extends AsyncTask<Integer, Void, JSONObject> {
    private int page;
    private TrendingsAdapter adapter;

    public GetSimilarTVShows(TrendingsAdapter adapter){
        this.adapter = adapter;
        page = 1;
    }

    public GetSimilarTVShows(int page) {
        this.page = page;
    }

    public GetSimilarTVShows() {
        page = 1;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();

        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "/similar?api_key=" + BuildConfig.TMDb_API_key  + "&page=" + page);
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
        try{
            ArrayList<TvShow> moviesArrayList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject movieJSON = null;
            for(int i = 0; i < jsonArray.length(); i++){
                movieJSON = jsonArray.getJSONObject(i);
                TvShow movies = new TvShow();
                String poster_url = movieJSON.getString("poster_path");
                String backdrop_url = movieJSON.getString("backdrop_path");
                String name = movieJSON.getString("name");
                String releaseDate = movieJSON.getString("first_air_date");
                int id = movieJSON.getInt("id");

                movies.setId(id);
                movies.setPoster(poster_url);
                movies.setBackdrop(backdrop_url);
                movies.setTitle(name);
                movies.setReleaseDate(releaseDate);
                moviesArrayList.add(movies);
            }
            adapter.setShowsList(moviesArrayList, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
