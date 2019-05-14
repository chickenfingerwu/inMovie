package com.inmovie.inmovie.MovieTvClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Entertainment implements Serializable {

    protected String poster_url;
    protected String banner_url;
    protected float rating;
    protected String directorName;

    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

    protected String title;


    public static class EntertainmentResult {
        private List<Entertainment> results;

        public List<Entertainment> getResults() {
            return results;
        }
    }

    @SerializedName("poster_path")
    protected String poster;

    @SerializedName("overview")
    protected String description;

    @SerializedName("backdrop_path")
    protected String backdrop;

    @SerializedName("id")
    protected Integer id;

    public Entertainment() {
    }

    public Entertainment(String n){
        title = n;
    }

    public Entertainment(String n, String p_url, String b_url){
        title = n;
        try {
            poster_url = p_url;
            banner_url = b_url;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public Entertainment(String n, String p_url, String b_url, String des){
        title = n;
        description = des;
        try {
            poster_url = p_url;
            banner_url = b_url;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addDescription(String des){
        description = des;
    }

    public String getPoster_url(){
        return TMDB_IMAGE_PATH + poster_url;
    }

    public String getBanner_url(){
        return banner_url;
    }

    public String getTitle(){
        return title;
    }

    public String getDirectorName() { return directorName;}
}

