package com.inmovie.inmovie.model.api.TMDb.Trending;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.HandlingTrending;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTrendingMovies extends AsyncTask<Void, Void, JSONObject> {
    private TrendingsAdapter adapter;
    private HandlingTrending handlingTrending;

    public GetTrendingMovies(TrendingsAdapter adapter, HandlingTrending handler){
        this.adapter = adapter;
        this.handlingTrending = handler;
    }

    public GetTrendingMovies() {}

    @Override
    protected JSONObject doInBackground(Void... voids) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/trending/movie/week?api_key=" + BuildConfig.TMDb_API_key);

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
