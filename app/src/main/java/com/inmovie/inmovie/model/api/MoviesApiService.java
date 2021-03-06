package com.inmovie.inmovie.model.api;

import com.inmovie.inmovie.MovieTvClasses.Movies;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;

import retrofit.Callback;
import retrofit.http.GET;

public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movies.MovieResult> cb);

    @GET("/tv/popular")
    void getTrendingShow(Callback<TvShow.TvShowResult> cb);

    @GET("/search/movie")
    void searchMovie(Callback<Movies.MovieResult> cb);
}
