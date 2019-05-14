package com.inmovie.inmovie.model.api.TMDb.Trending;

import android.os.AsyncTask;

import com.inmovie.inmovie.Adapters.TrendingsAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.MovieTvClasses.Movies;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetTrendingTV extends AsyncTask<Void, Void, JSONObject> {

    private TrendingsAdapter adapter;

    public GetTrendingTV(TrendingsAdapter adapter){
        this.adapter = adapter;
    }

    public GetTrendingTV() {}

    @Override
    protected JSONObject doInBackground(Void... voids) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/trending/tv/week?api_key=" + BuildConfig.TMDb_API_key);

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
