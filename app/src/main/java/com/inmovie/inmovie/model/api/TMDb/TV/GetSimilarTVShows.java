package com.inmovie.inmovie.model.api.TMDb.TV;

import android.os.AsyncTask;

import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/tv/" + integers[0] + "/similar?api_key=" + BuildConfig.TMDb_API_key  + "&page=" + page);

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
