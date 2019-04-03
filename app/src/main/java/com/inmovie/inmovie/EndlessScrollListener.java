package com.inmovie.inmovie;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    RecyclerView.LayoutManager mLayoutManager;

    //the minimum number of items to have below current scroll position
    //before loading more datas
    private int visibleThreshold = 5;

    //current number of datas (items) have loaded
    private int currentPage = 0;

    //total number of items after the last load
    private int previousTotalItem = 0;

    //check if still waiting for data to load
    private boolean loading = true;


    //the starting page index
    private int startingPageIndex = 0;

    public EndlessScrollListener(){
    }

    public EndlessScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public EndlessScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessScrollListener(int visibleThreshold){
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage){
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy){

        int lastVisibleItemPosition = 0;
        int totalItemsCount = mLayoutManager.getItemCount();


        if(mLayoutManager instanceof GridLayoutManager){
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        if(totalItemsCount < previousTotalItem){
            this.currentPage = this.startingPageIndex;
            this.previousTotalItem = totalItemsCount;
            if(totalItemsCount == 0){
                this.loading = true;
            }
        }

        if(loading && (totalItemsCount > previousTotalItem)){
            loading = false;
            previousTotalItem = totalItemsCount;
        }

        if(!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemsCount){
            currentPage++;
            onLoadMore(currentPage, totalItemsCount, view);
            loading = true;
        }

    }

    public void resetState(){
        this.currentPage = this.startingPageIndex;
        this.previousTotalItem = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

}
