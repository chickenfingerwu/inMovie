package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.Handlers.HandlingMovie;
import com.inmovie.inmovie.MovieTvClasses.Movies;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetVideos extends AsyncTask<Integer, Void, JSONArray> {

    protected Movies movie = null;
    protected HandlingMovie handler = null;

    public GetVideos() {}

    public GetVideos(Movies movie, HandlingMovie handler){
        this.movie = movie;
        this.handler = handler;
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        JSONArray result = new JSONArray();

        try {
            result = Network.getJSONObject("https://api.themoviedb.org/3/movie/" + integers[0] + "/videos?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US").getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                if (!result.getJSONObject(i).getString("site").equalsIgnoreCase("YouTube")) {
                    result.remove(i--);
                }
                else {
                    JSONObject temp = result.getJSONObject(i);
                    String youtube_url = "https://www.youtube.com/watch?v=" + temp.getString("key");
                    temp.remove("key");
                    temp.put("link", youtube_url);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray){
        if(movie != null){
            String link = "";
            if(jsonArray.length() > 0){
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    link = jsonObject.getString("link");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            movie.setTrailerUrl(link);
            if(handler != null){
                Bundle data = new Bundle();
                data.putSerializable("details", movie);
                Message message = new Message();
                message.setData(data);
                handler.sendMessage(message);
            }
        }
    }
}
