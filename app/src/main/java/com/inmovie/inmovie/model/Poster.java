package com.inmovie.inmovie.model;

import org.json.simple.JSONObject;

public class Poster implements Comparable {
    private String url;
    private double averageRating;
    private long ratingCount;

    public Poster(JSONObject posterInfo) {
        url = "https://www.thetvdb.com/banners/" + posterInfo.get("fileName");
        if (((JSONObject) posterInfo.get("ratingsInfo")).get("average") instanceof Double) {
            averageRating = (double) ((JSONObject) posterInfo.get("ratingsInfo")).get("average");
        }
        else {
            averageRating = Double.valueOf(Long.toString((long) ((JSONObject) posterInfo.get("ratingsInfo")).get("average")));
        }
        ratingCount = (long) ((JSONObject) posterInfo.get("ratingsInfo")).get("count");
    }

    @Override
    public String toString() {
        return url;
    }

    @Override
    public int compareTo(Object o) {
        Poster p = (Poster) o;
        if ((averageRating*ratingCount) == (p.averageRating*p.ratingCount)) {
            return 0;
        }
        else if ((averageRating*ratingCount) > (p.averageRating*p.ratingCount)) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
