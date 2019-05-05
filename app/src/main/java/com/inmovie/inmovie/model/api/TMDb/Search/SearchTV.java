package com.inmovie.inmovie.model.api.TMDb.Search;

import android.os.AsyncTask;

import com.inmovie.inmovie.Adapters.MovieSearchResultAdapter;
import com.inmovie.inmovie.BuildConfig;
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

public class SearchTV extends AsyncTask<String, Void, JSONArray> {
    private MovieSearchResultAdapter movieSearchResultAdapter;
    private int page;

    public SearchTV(MovieSearchResultAdapter tvAdapter, int page){
        movieSearchResultAdapter = tvAdapter;
        this.page = page;
    }

    public SearchTV() {
        page = 1;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        JSONArray result = new JSONArray();
        // strings[0]: name to search
        // strings[1]: page

        try {
            URL url = new URL("https://api.themoviedb.org/3/search/tv?api_key=" + BuildConfig.TMDb_API_key + "&query=" + strings[0] + "&page=" + page);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();

            String temp;
            while ((temp = reader.readLine()) != null) {
                response.append(temp);
            }

            JSONObject _temp = new JSONObject(response.toString());
            result = _temp.getJSONArray("results");
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute (JSONArray jsonArray){
        if (movieSearchResultAdapter != null) {
            ArrayList<Movies> tvList = new ArrayList<>();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    TvShow tvCandidate = new TvShow();

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String poster_path = jsonObject.getString("poster_path");
                        String title = jsonObject.getString("name");
                        String releaseDate = jsonObject.getString("first_air_date");

                        tvCandidate.setPoster(poster_path);
                        tvCandidate.setId(id);
                        tvCandidate.setTitle(title);
                        tvCandidate.setReleaseDate(releaseDate);
                        tvList.add(tvCandidate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (page != 1) {
                movieSearchResultAdapter.setMoviesList(tvList, false);
            } else {
                movieSearchResultAdapter.setMoviesList(tvList, true);
            }
        }
    }
}
