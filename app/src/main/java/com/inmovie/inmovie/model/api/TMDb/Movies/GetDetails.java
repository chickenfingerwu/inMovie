package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.Handlers.HandlingMovieRating;
import com.inmovie.inmovie.model.api.Network;
import com.inmovie.inmovie.model.api.OMDb.GetRating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import at.blogc.android.views.ExpandableTextView;

public class GetDetails extends AsyncTask<Integer, Void, JSONObject> {
    private TextView name = null;
    private ExpandableTextView overview = null;
    private ImageView backdrop = null;
    private ImageView poster = null;
    private Bitmap _backdrop = null;
    private Bitmap _poster = null;
    private TextView releaseYear = null;
    private TextView genres = null;
    private TextView releaseDate = null;
    private TextView runtime = null;
    private TextView additionalInfo = null;

    private HandlingMovieRating handler;


    public GetDetails() {}

    public GetDetails(List<View> views, HandlingMovieRating handler) {
        try {
            poster = (ImageView) views.get(0);
            releaseYear = (TextView) views.get(1);
            runtime = (TextView) views.get(2);
            name = (TextView) views.get(3);
            overview = (ExpandableTextView) views.get(4);
            backdrop = (ImageView) views.get(5);
            additionalInfo = (TextView) views.get(6);
            genres = (TextView) views.get(7);
            releaseDate = (TextView) views.get(8);
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        this.handler = handler;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/movie/" + integers[0] + "?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US");

        try {
            URL backdrop_url = new URL("https://image.tmdb.org/t/p/w1280" + result.getString("backdrop_path"));
            HttpsURLConnection connection = (HttpsURLConnection) backdrop_url.openConnection();

            connection.connect();
            int resCode = connection.getResponseCode();

            if (resCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                _backdrop = BitmapFactory.decodeStream(inputStream);
            }

        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        try {
            URL backdrop_url = new URL("https://image.tmdb.org/t/p/w500" + result.getString("poster_path"));
            HttpsURLConnection connection = (HttpsURLConnection) backdrop_url.openConnection();

            connection.connect();
            int resCode = connection.getResponseCode();

            if (resCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                _poster = BitmapFactory.decodeStream(inputStream);
            }

        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject rating = GetRating.getRatingByID(result.getString("imdb_id"));
            result.put("imdbRating", rating.getDouble("score"));
            result.put("imdbVotes", rating.getString("votes"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        // Set movie's title
        String _title = "";
        try {
            _title = jsonObject.getString("title");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (name != null)
            name.setText(_title);

        // Set movie's plot overview
        String _overview = "";
        try {
            _overview = jsonObject.getString("overview");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (overview != null)
            overview.setText(_overview);

        // Set movie's release date
        String _releaseDate = "";
        try {
            _releaseDate = jsonObject.getString("release_date");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (releaseDate != null) {
            if (!_releaseDate.equals(""))
                releaseDate.setText("Release Date: " + _releaseDate);
            else {
                releaseDate.setText("Release Date: Unknown");
            }
        }
        if(releaseYear != null){
            if (!_releaseDate.equals(""))
                releaseYear.setText(_releaseDate.split("-")[0]);
            else {
                releaseYear.setText("Unknown Year");
            }
        }
        // Set movie's genres
        StringBuilder _genres = new StringBuilder("Genres: ");
        try {
            JSONArray genre = jsonObject.getJSONArray("genres");
            for (int i = 0; i < genre.length(); i++) {
                _genres.append(genre.getJSONObject(i).getString("name") + (i == genre.length() - 1?"":", "));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (genres != null)
            genres.setText(_genres.toString());

        // Set movie's runtime
        int _runtime = -1;
        try {
            if (jsonObject.get("runtime") != null) {
                _runtime = jsonObject.getInt("runtime");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        // Set movie's backdrop image
        if (backdrop != null) {
            if (_backdrop != null) {
                backdrop.setImageBitmap(_backdrop);
            }
        }

        // Set movie's poster
        if (poster != null) {
            if (_poster != null) {
                poster.setImageBitmap(_poster);
            }
        }

        // Set movie's rating
        Double imdb_score = null;
        String imdb_votes = null;
        Double tmdb_score = null;
        Integer tmdb_votes = null;
        try{
            imdb_score = jsonObject.getDouble("imdbRating");
            imdb_votes = jsonObject.getString("imdbVotes");
            tmdb_score = jsonObject.getDouble("vote_average");
            tmdb_votes = jsonObject.getInt("vote_count");
            Bundle ratingData = new Bundle();
            ratingData.putDouble("imdbRating", imdb_score);
            ratingData.putDouble("tmdbRating", tmdb_score);
            ratingData.putString("imdbVotes", imdb_votes);
            ratingData.putInt("tmdbVotes", tmdb_votes);
            Message m = new Message();
            m.setData(ratingData);
            handler.sendMessage(m);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String tagline = null;
        try {
            tagline = jsonObject.getString("tagline");
            if (additionalInfo != null) {
                additionalInfo.setText(tagline);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(genres != null){
            if(_genres != null) {
                genres.setText(_genres.toString());
            }
        }

        if(runtime != null){
            if(_runtime > 0){
                runtime.setText(_runtime + " mins");
            }
            else {
                runtime.setText("Unknown");
            }
        }

    }
}
