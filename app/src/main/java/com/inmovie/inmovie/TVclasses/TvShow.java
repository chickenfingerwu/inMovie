package com.inmovie.inmovie.TVclasses;

import com.inmovie.inmovie.Movies;

import java.util.List;

public class TvShow extends Movies {
    protected TvSeason seasons;
    int numbofSeason = 0;

    public static class TvShowResult {
        private List<TvShow> results;

        public List<TvShow> getResults() {
            return results;
        }
    }

    public TvSeason getSeasons() {
        return seasons;
    }

    public void setSeasons(TvSeason seasons) {
        this.seasons = seasons;
    }

    public int getNumbofSeason() {
        return numbofSeason;
    }

    public void setNumbofSeason(int numbofSeason) {
        this.numbofSeason = numbofSeason;
    }


}
