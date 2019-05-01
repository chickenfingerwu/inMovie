package com.inmovie.inmovie.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.inmovie.inmovie.Adapters.EndlessScrollListener;
import com.inmovie.inmovie.Adapters.MovieSearchResultAdapter;
import com.inmovie.inmovie.Adapters.ResultPagerAdapter;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.model.api.TMDb.Search.SearchMovies;

public class MoviesSearchResult extends AppCompatActivity {

    private ResultPagerAdapter resultPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private EndlessScrollListener scrollListener;
    private RecyclerView recyclerView;
    private MovieSearchResultAdapter movieSearchResultAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_search_result);

        //Setup RecyclerView, get Intent and set Adapter
        Intent intent = getIntent();
        String searchToken = intent.getStringExtra("movie_searched");

        SearchView searchView = (SearchView) findViewById(R.id.searched_bar);
        searchView.setSubmitButtonEnabled(true);
        searchView.setFocusable(false);
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setQuery(searchToken, false);


        resultPagerAdapter = new ResultPagerAdapter(getSupportFragmentManager(), 2, searchToken, 1);
        viewPager = (ViewPager) findViewById(R.id.result_tabs);
        viewPager.setAdapter(resultPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Movie");
        tabLayout.getTabAt(1).setText("TV Show");

        //Listen for searches by the user on this activity
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                //get which tab the user was selecting
                int prevPos = tabLayout.getSelectedTabPosition();

                //reset the data of the fragments;
                resultPagerAdapter.setPage(1);
                resultPagerAdapter.setQuery(query);
                resultPagerAdapter.notifyDataSetChanged();

                //refresh the adapter
                viewPager.setAdapter(resultPagerAdapter);

                //re-select the previous tab the user was browsing
                tabLayout.getTabAt(prevPos).select();

                //reset the text because i still can't make them not disappear every time refreshing
                tabLayout.getTabAt(0).setText("Movie");
                tabLayout.getTabAt(1).setText("TV Show");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    //Get movies through the API, query is the search token, page is the number of the result page
    private void getMoreMovie(String query, int page){
        new SearchMovies(movieSearchResultAdapter, page).execute(query);
    }

    /*
    //No need for this function but just keep it here just in case!
    private void getMoviesResultFromRestAdapter(final String query, final int page){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.TMDb_API_key);
                        request.addEncodedQueryParam("query", query);
                        request.addEncodedQueryParam("page", Integer.toString(page));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.searchMovie(new Callback<com.inmovie.inmovie.Movies.MovieResult>() {
            @Override
            public void success(com.inmovie.inmovie.Movies.MovieResult movieResult, Response response) {
                if (page != 1) {
                    movieSearchResultAdapter.setMoviesList(movieResult.getResults(), false);
                }
                else{
                    movieSearchResultAdapter.setMoviesList(movieResult.getResults(), true);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }*/
}
