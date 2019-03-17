package com.inmovie.inmovie.model;

import com.inmovie.inmovie.model.api.OMDb;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Series implements Serializable {
    private long id; // TheTVDB Series ID
    private String seriesName; // Name
    private String banner; // Banner link
    private String status; // Continuing or Finished
    private String firstAired;
    private String runtime;
    private String[] genre;
    private String overview;
    private String rating; // Age rating
    private String imdbId;
    private double imdbRating;
    private ArrayList<Actor> actors;
    private String poster; // Poster links

    public Series(JSONObject seriesInfo, JSONArray actorsInfo, ArrayList<Poster> posters) {
        id = (long) seriesInfo.get("id");
        seriesName = (String) seriesInfo.get("seriesName");
        banner = "https://www.thetvdb.com/banners/" + seriesInfo.get("banner");
        status = (String) seriesInfo.get("status");
        firstAired = (String) seriesInfo.get("firstAired");
        runtime = (String) seriesInfo.get("runtime");
        overview = (String) seriesInfo.get("overview");
        rating = (String) seriesInfo.get("rating");
        imdbId = (String) seriesInfo.get("imdbId");
        imdbRating = new OMDb().getIMDbRating(imdbId);
        genre = new String[((JSONArray) seriesInfo.get("genre")).size()];
        for (int i = 0; i < genre.length; i++) {
            genre[i] = (String) ((JSONArray) seriesInfo.get("genre")).get(i);
        }

        actors = new ArrayList<>();
        for (int i = 0; i < actorsInfo.size(); i++) {
            actors.add(new Actor((JSONObject) actorsInfo.get(i)));
        }
        Collections.sort(posters);
        this.poster = posters.get(0).toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("ID: " + id + "\nSeries: " + seriesName +
                "\nPoster link: " + poster +
                "\nBanner link: " + banner + "\nStatus: " + status +
                "\nFirst aired: " + firstAired + "\nRuntime: " + runtime + "\nGenre(s): ");
        for (int i = 0; i < genre.length; i++) {
            result.append(genre[i] + (i == genre.length-1 ? "": ", "));
        }
        result.append("\nActors: ");
        for (int i = 0; i < actors.size(); i++) {
            result.append(actors.get(i).getName() + (i != actors.size() - 1?", ":""));
        }
        result.append("\nOverview: " + overview + "\nRating: " + rating + "\nIMDB ID: " + imdbId +
                "\nIMDb rating: " + imdbRating);
        return result.toString();
    }

    public long getId() {
        return id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public String getBanner() {
        return banner;
    }

    public String getStatus() {
        return status;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getRuntime() {
        return runtime;
    }

    public String[] getGenre() {
        return genre;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public String getPoster() {
        return poster;
    }
}
