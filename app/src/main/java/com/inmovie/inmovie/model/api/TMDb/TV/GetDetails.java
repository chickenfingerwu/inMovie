package com.inmovie.inmovie.model.api.TMDb.TV;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.HandlingTvShow;
import com.inmovie.inmovie.TVclasses.TvShow;
import com.inmovie.inmovie.model.api.OMDb.GetRating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private TextView genres = null;
    private TextView first_air_date = null;
    private TextView runtime = null;
    private TextView rating = null;
    private RatingBar ratingBar = null;
    private TextView additionalInfo = null;
    private TextView genres_runtime = null;

    TvShow show;
    HandlingTvShow handler;
    public GetDetails(ArrayList<View> views, TvShow show) {
        try {
            name = (TextView) views.get(0);
            overview = (TextView) views.get(1);
            backdrop = (ImageView) views.get(2);
            rating = (TextView) views.get(3);
            ratingBar = (RatingBar) views.get(4);
            additionalInfo = (TextView) views.get(5);
            genres_runtime = (TextView) views.get(6);
            first_air_date = (TextView) views.get(7);
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        this.show = show;
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
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "?api_key=" + BuildConfig.TMDb_API_key  + "&append_to_response=external_ids");
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
        System.out.println("onPostExecute");
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
        // Set movie's rating
        Double score = null;
        String votes = null;
        try{
            score = jsonObject.getDouble("imdbRating");
            votes = jsonObject.getString("imdbVotes");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(score != null){
            if(ratingBar != null && rating!=null) {
                rating.setText(Double.toString(score) + " IMDb (" + votes + " votes)");
                ratingBar.setRating(score.floatValue());
            }
        }
        else {
            if (rating != null)
                rating.setText("Not yet rated");
        }
        if(show!=null){
            if(score!=null) {
                show.setRating(score);
            }
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
        if (!_first_air_date.equals(""))
            if(first_air_date!=null) {
                first_air_date.setText("Release Date: " + _first_air_date);
            }
            if(show!=null){
                show.setReleaseDate(_first_air_date);
            }
        else if(first_air_date!=null && _first_air_date.equals("")){
            first_air_date.setText("Release Date: Unknown");
                if(show!=null){
                    show.setReleaseDate("Unknown");
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
                runtime.setText("Runtime: Unknown");
            } else {
                runtime.setText("Runtime: " + rt + " minutes");
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

        if(additionalInfo != null){
            if(_genres != null) {
                additionalInfo.setText(_genres.toString() + " | Runtime: " + rt + " minutes");
            }
        }

        if(genres_runtime != null){
            if(_genres != null) {
                genres_runtime.setText(_genres.toString() + " | Runtime: " + rt + " minutes");
            }
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
