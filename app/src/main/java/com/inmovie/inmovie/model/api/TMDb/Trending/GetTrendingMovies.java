package com.inmovie.inmovie.model.api.TMDb.Trending;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.HandlingTrending;
import com.inmovie.inmovie.Movies;

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

public class GetTrendingMovies extends AsyncTask<Void, Void, JSONObject> {
    private TrendingsAdapter adapter;
    private HandlingTrending handlingTrending;

    public GetTrendingMovies(TrendingsAdapter adapter, HandlingTrending handler){
        this.adapter = adapter;
        this.handlingTrending = handler;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/trending/movie/week?api_key=" + BuildConfig.TMDb_API_key);
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
            ArrayList<Movies> moviesArrayList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            JSONObject movieJSON = null;
            for(int i = 0; i < jsonArray.length(); i++){
                movieJSON = jsonArray.getJSONObject(i);
                Movies movies = new Movies();
                String poster_url = movieJSON.getString("poster_path");
                String backdrop_url = movieJSON.getString("backdrop_path");
                String name = movieJSON.getString("title");
                String releaseDate = movieJSON.getString("release_date");
                int id = movieJSON.getInt("id");

                movies.setId(id);
                movies.setPoster(poster_url);
                movies.setBackdrop(backdrop_url);
                movies.setTitle(name);
                movies.setReleaseDate(releaseDate);
                moviesArrayList.add(movies);
            }
            adapter.setMoviesList(moviesArrayList, true);

            Bundle hottestData = new Bundle();
            hottestData.putSerializable("hottest", moviesArrayList.get(0));
            Message message = new Message();
            message.setData(hottestData);
            handlingTrending.sendMessage(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
