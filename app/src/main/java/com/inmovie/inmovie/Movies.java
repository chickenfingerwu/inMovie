package com.inmovie.inmovie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movies implements Serializable {
    public static final String TMDB_POSTER_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";
    public static final String TMDB_BANNER_IMAGE_PATH = "http://image.tmdb.org/t/p/w1280";

    protected double rating;

    protected String genres;

    protected int runtime;

    @SerializedName("title")
    protected String title;

    @SerializedName("poster_path")
    protected String poster;

    @SerializedName("overview")
    protected String description;

    @SerializedName("backdrop_path")
    protected String backdrop = null;

    @SerializedName("id")
    protected Integer id;

    @SerializedName("release_date")
    protected String releaseDate = "";

    protected String trailerUrl = "";

    List<Actor> actors;

    public Movies() {}

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_url() {
        return TMDB_POSTER_IMAGE_PATH + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setId(int i){
        this.id = i;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return TMDB_BANNER_IMAGE_PATH + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTrailerUrl(){ return trailerUrl;}

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public static class MovieResult {
        private List<Movies> results;

        public List<Movies> getResults() {
            return results;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}

