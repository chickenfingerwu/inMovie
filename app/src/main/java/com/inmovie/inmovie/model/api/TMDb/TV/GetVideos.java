package com.inmovie.inmovie.model.api.TMDb.TV;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.HandlingTvShow;
import com.inmovie.inmovie.TVclasses.TvShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetVideos extends AsyncTask<Integer, Void, JSONArray> {

    private TvShow show;
    private HandlingTvShow handler;

    public GetVideos(TvShow show, HandlingTvShow handler){
        this.show = show;
        this.handler = handler;
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        JSONArray result = new JSONArray();

        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "/videos?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String input;
            while ((input = bufferedReader.readLine()) != null) {
                builder.append(input);
            }

            result = new JSONObject(builder.toString()).getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                if (!result.getJSONObject(i).getString("site").equalsIgnoreCase("YouTube")) {
                    result.remove(i--);
                }
                else {
                    JSONObject temp = result.getJSONObject(i);
                    String youtube_url = "https://www.youtube.com/embed/" + temp.getString("key");
                    temp.remove("key");
                    temp.put("link", youtube_url);
                }
            }
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray){
        if(show != null){
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
            show.setTrailerUrl(link);
            if(handler != null){
                Bundle data = new Bundle();
                data.putSerializable("details", show);
                Message message = new Message();
                message.setData(data);
                handler.sendMessage(message);
            }
        }
    }
}
