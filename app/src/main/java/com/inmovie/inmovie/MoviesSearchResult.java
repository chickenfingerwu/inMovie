package com.inmovie.inmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.inmovie.inmovie.model.api.TMDb.Movies.SearchContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MoviesSearchResult extends AppCompatActivity {

    private EndlessScrollListener scrollListener;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_search_result);

        Intent intent = getIntent();
        final String searchToken = intent.getStringExtra("movie_searched");

        SearchView searchView = (SearchView) findViewById(R.id.searched_bar);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setQuery(searchToken, false);

        recyclerView = (RecyclerView) findViewById(R.id.search_result);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.setLayoutManager(layoutManager);

        imageAdapter = new ImageAdapter(this);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        ArrayList<Movies> movies = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new SearchContent().execute(searchToken).get();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(jsonArray!=null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Movies movies1 = new Movies();
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String poster_path = jsonObject.getString("poster_path");
                    movies1.setPoster(poster_path);
                    movies1.setId(id);
                    movies.add(movies1);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        imageAdapter.setMoviesList(movies, false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    ArrayList<Movies> moviesArrayList = new ArrayList<>();
                    JSONArray jsonArray1 = null;
                    try {
                        jsonArray1 = new SearchContent().execute(query).get();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if(jsonArray1!=null) {
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            Movies movieCandidate = new Movies();
                            try {
                                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String poster_path = jsonObject.getString("poster_path");
                                movieCandidate.setPoster(poster_path);
                                movieCandidate.setId(id);
                                moviesArrayList.add(movieCandidate);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        imageAdapter.setMoviesList(moviesArrayList, true);

                    }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        /*
        ArrayList<Movies> movies = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            movies.add(new Movies());
        }*/




        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {


            }
        };

        recyclerView.addOnScrollListener(scrollListener);

    }

    private void getMoviesResultFromRestAdapter(final String query){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", Keys.TMDB_APIKey);
                        request.addEncodedQueryParam("query", query);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getPopularMovies(new Callback<Movies.MovieResult>() {
            @Override
            public void success(Movies.MovieResult movieResult, Response response) {
                imageAdapter.setMoviesList(movieResult.getResults(), false);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
