package com.inmovie.inmovie.Adapters.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inmovie.inmovie.Adapters.EndlessScrollListener;
import com.inmovie.inmovie.Adapters.MovieSearchResultAdapter;
import com.inmovie.inmovie.DividerItemDecoration;
import com.inmovie.inmovie.R;
import com.inmovie.inmovie.model.api.TMDb.Search.SearchMovies;

public class MovieResultFragment extends Fragment {

    private EndlessScrollListener scrollListener;
    private RecyclerView recyclerView;
    private MovieSearchResultAdapter movieSearchResultAdapter;
    private LinearLayoutManager layoutManager;
    protected int page;
    protected String query;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.movie_result_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //Get search token, page
        Bundle args = getArguments();
        query = args.getString("query");
        page = args.getInt("page");

        recyclerView = view.findViewById(R.id.movie_tab);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        movieSearchResultAdapter = new MovieSearchResultAdapter(view.getContext());
        recyclerView.setAdapter(movieSearchResultAdapter);

        getMoreMovie(query, page);

        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getMoreMovie(query, ++page);
            }
        };

        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.white_divider)));
        recyclerView.addOnScrollListener(scrollListener);
    }

    public void setQuery(String q){
        query = q;
    }

    private void getMoreMovie(String query, int page){
        new SearchMovies(movieSearchResultAdapter, page).execute(query);
    }
}
