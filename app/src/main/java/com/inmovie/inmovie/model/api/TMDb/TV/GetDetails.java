package com.inmovie.inmovie.model.api.TMDb.TV;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.Handlers.HandlingTvRating;
import com.inmovie.inmovie.Handlers.HandlingTvShow;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;
import com.inmovie.inmovie.model.api.Network;
import com.inmovie.inmovie.model.api.OMDb.GetRating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class GetDetails extends AsyncTask<Integer, Void, JSONObject> {
    private TextView name = null;
    private TextView overview = null;
    private ImageView backdrop = null;
    private ImageView poster = null;
    private Bitmap _backdrop = null;
    private Bitmap _poster = null;
    private TextView director = null;
    private TextView releaseYear = null;
    private TextView genres = null;
    private TextView first_air_date = null;
    private TextView runtime = null;
    private TextView additionalInfo = null;

    private TvShow show;
    private HandlingTvShow handler;
    private HandlingTvRating ratingHandler;

    public GetDetails(ArrayList<View> views, TvShow show, HandlingTvRating ratingHandler, HandlingTvShow handler) {
        try {
            director = (TextView) views.get(0);
            releaseYear = (TextView) views.get(1);
            runtime = (TextView) views.get(2);
            poster = (ImageView) views.get(3);
            overview = (TextView) views.get(4);
            backdrop = (ImageView) views.get(5);
            additionalInfo = (TextView) views.get(6);
            genres = (TextView) views.get(7);
            first_air_date = (TextView) views.get(8);
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        this.show = show;
        this.ratingHandler = ratingHandler;
        this.handler = handler;
    }

    public GetDetails(TvShow show, HandlingTvShow handler, HandlingTvRating ratingHandler){
        this.show = show;
        this.handler = handler;
        this.ratingHandler = ratingHandler;
    }

    public GetDetails(TvShow show, HandlingTvShow handler){
        this.show = show;
        this.handler = handler;
    }

    public GetDetails() {

    }

    public void setShow(TvShow s){
        show = s;
    }

    public void setHandler(HandlingTvShow h){
        handler = h;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/tv/" + integers[0] + "?api_key=" + BuildConfig.TMDb_API_key  + "&append_to_response=external_ids");

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
            JSONObject rating = GetRating.getRatingByID(result.getJSONObject("external_ids").getString("imdb_id"));
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
        // Set show's title
        String _title = "";
        try {
            _title = jsonObject.getString("name");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (name != null) {
            name.setText(_title);
        }
        if(show!=null){
            show.setTitle(_title);
        }

        //Set show's poster
        if(poster != null) {
            if (_poster != null) {
                poster.setImageBitmap(_poster);
            }
        }

        //Set show's id
        Integer id = null;
        try{
            id = jsonObject.getInt("id");
            if(show!=null && id!=null) {
                show.setId(id);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Set show's number of seasons
        int seasons = 0;
        try{
            seasons = jsonObject.getInt("number_of_seasons");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(show != null && seasons > 0){
            show.setNumbofSeason(seasons);
        }

        // Set show's plot overview
        String _overview = "";
        try {
            _overview = jsonObject.getString("overview");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (overview != null) {
            overview.setText(_overview);
        }
        if(show!=null){
            show.setDescription(_overview);
        }
        // Set show's first air date
        String _first_air_date = "";
        try {
            _first_air_date = jsonObject.getString("first_air_date");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (!_first_air_date.equals("")) {
            if (first_air_date != null) {
                first_air_date.setText("Release Date: " + _first_air_date);
            }
            if (show != null) {
                show.setReleaseDate(_first_air_date);
            }

            if(releaseYear != null){
                releaseYear.setText(_first_air_date.split("-")[0]);
            }
        }
        else if(first_air_date!=null){
            first_air_date.setText("Release Date: Unknown");
            if(show!=null){
                show.setReleaseDate("Unknown");
            }
            if(releaseYear != null){
                releaseYear.setText("Year Unknown");
            }
        }

        //Set show's creators
        try {
            JSONArray creators = jsonObject.getJSONArray("created_by");
            JSONObject c = null;
            if(director != null) {
                for (int i = 0; i < creators.length(); i++) {
                    c = creators.getJSONObject(i);
                    String cName = c.getString("name");
                    if (director.getText().equals("")) {
                        director.setText(cName);
                    } else {
                        director.append(", " + cName);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
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
        int rt = 0;
        try {
            if (jsonObject.get("episode_run_time") != null) {
                JSONArray runtimes = jsonObject.getJSONArray("episode_run_time");
                int min = runtimes.getInt(0);
                for (int i = 0; i < runtimes.length(); i++) {
                    if(runtimes.getInt(i) < min){
                        min = runtimes.getInt(i);
                    }
                }
                rt = min;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (runtime != null) {
            if (rt == 0) {
                runtime.setText("Unknown");
            } else {
                runtime.setText(rt + " mins");
            }
        }
        if(show!=null){
            show.setRuntime(rt);
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

        String status = "";
        try {
            status = jsonObject.getString("status");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(additionalInfo != null && !status.equals("")){
            additionalInfo.setText(status);
        }

        if(show!=null){
            show.setGenres(_genres.toString());
        }
        if(show!=null) {
            try {
                show.setBackdrop(jsonObject.getString("backdrop_path"));
                show.setPoster(jsonObject.getString("poster_path"));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        // Set movie's rating
        Double imdb_score = null;
        String imdb_votes = null;
        Double tmdb_score = null;
        Integer tmdb_votes = null;
        if(ratingHandler != null) {
            try {
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
                ratingHandler.sendMessage(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(show!=null){
            if(imdb_score!=null) {
                show.setRating(imdb_score);
            }
        }

        if(handler!= null && show!=null){
            Bundle data = new Bundle();
            Message m = new Message();
            data.putSerializable("details", show);
            data.putSerializable("seasons", show);
            m.setData(data);
            handler.sendMessage(m);
;
        }


    }
}
