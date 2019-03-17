package com.inmovie.inmovie.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private long id; // TMDb ID
    private String imdb_id; // IMDb ID
    private String overview;
    private String poster_path;
    private String title; // Movie's name
    private String release_date; // Format: YYYY-MM-DD
    private String imdbRating;

}
