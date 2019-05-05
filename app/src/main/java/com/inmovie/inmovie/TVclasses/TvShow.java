package com.inmovie.inmovie.TVclasses;

import com.inmovie.inmovie.Movies;

import java.util.List;

public class TvShow extends Movies {
    TvSeason seasons;
    int numbofSeason = 3;

    public static class TvResult {
        private List<TvShow> results;

        public List<TvShow> getResults() {
            return results;
        }
    }

    public int getNumbofSeason() {
        return numbofSeason;
    }

    public void setNumbofSeason(int numbofSeason) {
        this.numbofSeason = numbofSeason;
    }
}
