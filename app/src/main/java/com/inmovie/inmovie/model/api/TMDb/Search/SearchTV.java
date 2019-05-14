package com.inmovie.inmovie.model.api.TMDb.Search;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.inmovie.inmovie.Adapters.MovieSearchResultAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.MovieTvClasses.Movies;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchTV extends AsyncTask<String, Void, JSONArray> {
    private MovieSearchResultAdapter movieSearchResultAdapter;
    private int page;
    private ShimmerFrameLayout mShimmer;
    private RecyclerView showView;

    public SearchTV(MovieSearchResultAdapter tvAdapter, int page){
        movieSearchResultAdapter = tvAdapter;
        this.page = page;
    }

    public SearchTV(MovieSearchResultAdapter tvAdapter, int page, RecyclerView showView, ShimmerFrameLayout mShimmer){
        movieSearchResultAdapter = tvAdapter;
        this.page = page;
        this.showView = showView;
        this.mShimmer = mShimmer;
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
            result = Network.getJSONObject("https://api.themoviedb.org/3/search/tv?api_key=" + BuildConfig.TMDb_API_key + "&query=" + strings[0] + "&page=" + page).getJSONArray("results");
        }
        catch (JSONException e) {
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
            if(mShimmer != null) {
                if (mShimmer.isAnimationStarted()) {
                    mShimmer.stopShimmerAnimation();
                    if (showView != null) {
                        if (tvList.size() > 0) {
                            showView.setVisibility(View.VISIBLE);
                        } else {
                            showView.setVisibility(View.INVISIBLE);
                        }
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
