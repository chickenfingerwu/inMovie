package com.inmovie.inmovie.MovieTvClasses.TvClasses;

import com.inmovie.inmovie.MovieTvClasses.Movies;

//Subclass to Movies since every episode is like a small movie
public class Episode extends Movies {
    private int tvID;
    private int seasonNumber;
    private int episodeNumber;

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getTvID() {
        return tvID;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void setTvID(int tvID) {
        this.tvID = tvID;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
}
