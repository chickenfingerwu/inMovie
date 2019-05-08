package com.inmovie.inmovie.model.api.TMDb.TV.Seasons.Episode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.HandlingEpisode;
import com.inmovie.inmovie.TVclasses.Episode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class GetDetails extends AsyncTask<Integer, Void, JSONObject> {
    /**
     * Get details about a season of TV series
     * @param integers TV ID, season number and episode number
     * @return Details
     */

    private HandlingEpisode handler;
    private Episode episode = null;
    private TextView episodeName = null;
    private TextView episodeSeason = null;
    private TextView episodeDescription = null;
    private ImageView episodeBanner = null;
    private TextView episodeReleaseDate = null;

    public GetDetails(ArrayList<View> views, Episode episode, HandlingEpisode handler) {
        try {
            episodeName = (TextView) views.get(0);
            episodeSeason = (TextView) views.get(1);
            episodeDescription = (TextView) views.get(2);
            episodeBanner = (ImageView) views.get(3);
            episodeReleaseDate = (TextView) views.get(4);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        this.episode = episode;
        this.handler = handler;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();

        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "/season/" + integers[1] + "/episode/" + integers[2] + "?api_key=" + BuildConfig.TMDb_API_key);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }

            result = new JSONObject(builder.toString());
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject){
        try{
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String overview = jsonObject.getString("overview");
            String banner = jsonObject.getString("still_path");
            String air_date = jsonObject.getString("air_date");
            Integer season = jsonObject.getInt("season_number");
            Integer episode_number = jsonObject.getInt("episode_number");

            episode.setId(id);
            episode.setTitle(name);
            episode.setDescription(overview);
            episode.setBackdrop(banner);
            episode.setReleaseDate(air_date);
            episode.setSeasonNumber(season);
            episode.setEpisodeNumber(episode_number);

            episodeName.setText(name);
            episodeDescription.setText(overview);
            episodeReleaseDate.setText(air_date);

            String episode_season = "Season: " + season.toString() + " Episode: " + episode_number.toString();
            episodeSeason.setText(episode_season);

            Bundle data = new Bundle();
            data.putSerializable("details", episode);
            Message message = new Message();
            message.setData(data);
            handler.sendMessage(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
