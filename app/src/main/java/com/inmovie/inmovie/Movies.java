package com.inmovie.inmovie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movies implements Serializable {
    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

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
    protected String releaseDate;

    List<Actor> actors;

    public Movies() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_url() {
        return TMDB_IMAGE_PATH + poster;
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
        return TMDB_IMAGE_PATH  + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
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
}

