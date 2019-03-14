package com.inmovie.inmovie.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;

public class Episode implements Serializable {
    private long airedEpisodeNumber;
    private long airedSeason;
    private String[] directors;
    private String episodeName;
    private String firstAired;
    private long id;
    private String overview;
    // Thumbnail
    private String filename;
    private Integer width;
    private Integer height;

    public Episode(JSONObject episodeInfo) {
        airedEpisodeNumber = (long) episodeInfo.get("airedEpisodeNumber");
        airedSeason = (long) episodeInfo.get("airedSeason");
        directors = new String[((JSONArray) episodeInfo.get("directors")).size()];
        for (int i = 0; i < directors.length; i++) {
            directors[i] = (String) ((JSONArray) episodeInfo.get("directors")).get(i);
        }
        episodeName = (String) episodeInfo.get("episodeName");
        firstAired = (String) episodeInfo.get("firstAired");
        id = (long) episodeInfo.get("id");
        overview = (String) episodeInfo.get("overview");
        filename = (String) episodeInfo.get("filename");
        width = episodeInfo.get("width")!=null?Integer.valueOf((String) episodeInfo.get("width")):0;
        height = episodeInfo.get("height")!=null?Integer.valueOf((String) episodeInfo.get("height")):0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(  "Episode name: " + episodeName + "\nSeason " + airedSeason +
                " Episode " + airedEpisodeNumber + "\nYear: " +
                firstAired.substring(0, 4) + "\nOverview: " + overview + "\n");

        return sb.toString();
    }
}
