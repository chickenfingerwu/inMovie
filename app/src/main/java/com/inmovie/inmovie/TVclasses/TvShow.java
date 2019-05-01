package com.inmovie.inmovie.TVclasses;

import com.inmovie.inmovie.Movies;

import java.util.List;

public class TvShow extends Movies {


    public static class TvResult {
        private List<TvShow> results;

        public List<TvShow> getResults() {
            return results;
        }
    }

}
