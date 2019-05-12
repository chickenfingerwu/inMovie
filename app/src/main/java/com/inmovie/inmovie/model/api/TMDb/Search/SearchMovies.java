package com.inmovie.inmovie.model.api.TMDb.Search;

import android.os.AsyncTask;

import com.inmovie.inmovie.Adapters.MovieSearchResultAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.Movies;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchMovies extends AsyncTask<String, Void, JSONArray> {
    private MovieSearchResultAdapter movieSearchResultAdapter;
    private int page;

    public SearchMovies(MovieSearchResultAdapter adapter, int p){
        movieSearchResultAdapter = adapter;
        page = p;
    }

    public SearchMovies() {
        page = 1;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        JSONArray result = new JSONArray();
        try {
            result = Network.getJSONObject("https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDb_API_key + "&query=" + strings[0] + "&page=" + page).getJSONArray("results");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute (JSONArray jsonArray){
        if (movieSearchResultAdapter != null) {
            ArrayList<Movies> moviesList = new ArrayList<>();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    com.inmovie.inmovie.Movies movieCandidate = new com.inmovie.inmovie.Movies();

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String poster_path = jsonObject.getString("poster_path");
                        String title = jsonObject.getString("title");
                        String releaseDate = jsonObject.getString("release_date");

                        movieCandidate.setPoster(poster_path);
                        movieCandidate.setId(id);
                        movieCandidate.setTitle(title);
                        movieCandidate.setReleaseDate(releaseDate);
                        moviesList.add(movieCandidate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (page != 1) {
                movieSearchResultAdapter.setMoviesList(moviesList, false);
            } else {
                movieSearchResultAdapter.setMoviesList(moviesList, true);
            }
        }
    }
}