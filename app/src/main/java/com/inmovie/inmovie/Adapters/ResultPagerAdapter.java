package com.inmovie.inmovie.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.inmovie.inmovie.Adapters.Fragments.MovieResultFragment;
import com.inmovie.inmovie.Adapters.Fragments.TvResultFragment;

/**
 * This class manages which tab should be which fragment,
 * in this case the result page when user searches will have 2 tabs,
 * first tab is for the movies results - MovieResultFragment, 2nd tab is
 * for the tv show results - TvResultFragment
 */

public class ResultPagerAdapter extends FragmentStatePagerAdapter {

    /**
     * @numbofTabs: number of tabs
     * @page: query page
     * @query: search query
     */
    protected int numbOfTabs;
    protected int page;
    protected String query;

    public ResultPagerAdapter(FragmentManager fm, int numbOfTabs, String query, int page){
        super(fm);
        this.numbOfTabs = numbOfTabs;
        this.query = query;
        this.page = page;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:

                //send the search query and which page to query to the movie fragment
                MovieResultFragment movieTab = new MovieResultFragment();
                Bundle args = new Bundle();
                args.putString("query", query);
                args.putInt("page", page);
                movieTab.setArguments(args);
                return movieTab;
            case 1:

                //send the search query and which page to query to the tv fragment
                TvResultFragment tvTab = new TvResultFragment();
                Bundle args1 = new Bundle();
                args1.putString("query", query);
                args1.putInt("page", page);
                tvTab.setArguments(args1);
                return tvTab;
                default:
                    return null;
        }
    }

    @Override
    public int getCount(){
        return numbOfTabs;
    }

    public void setPage(int p){
        page = p;
    }

    public void setQuery(String q){
        query = q;
    }
}
