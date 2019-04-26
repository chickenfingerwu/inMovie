package com.inmovie.inmovie.model.api.TMDb.TV;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;
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
    private TextView name;
    private TextView overview;
    private ImageView backdrop;
    private ImageView poster;
    private Bitmap _backdrop;
    private Bitmap _poster;
    private TextView genres;
    private TextView first_air_date;
    private TextView runtime;
    private TextView rating;

    public GetDetails(ArrayList<View> views) {
        name = views.get(0) == null?null:(TextView) views.get(0);
        overview = views.get(1) == null?null:(TextView)  views.get(1);
        backdrop = views.get(2) == null?null:(ImageView) views.get(2);
        poster = views.get(3) == null?null:(ImageView) views.get(3);
        genres = views.get(4) == null?null:(TextView) views.get(4);
        first_air_date = views.get(5) == null?null:(TextView) views.get(5);
        runtime = views.get(6) == null?null:(TextView) views.get(6);
        rating = views.get(7) == null?null:(TextView) views.get(7);
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/tv/" + integers[0] + "?api_key=" + BuildConfig.TMDb_API_key);
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
            URL backdrop_url = new URL("https://image.tmdb.org/t/p/w500" + result.getString("backdrop_path"));
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
            JSONObject rating = GetRating.getRatingByName(result.getString("name"));
            result.put("imdbRating", rating.getDouble("score"));
            result.put("imdbVotes", rating.getInt("votes"));
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
        if (name != null)
            name.setText(_title);

        // Set show's plot overview
        String _overview = "";
        try {
            _overview = jsonObject.getString("overview");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (overview != null)
            overview.setText(_overview);

        // Set show's first air date
        String _first_air_date = "";
        try {
            _first_air_date = jsonObject.getString("first_air_date");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (first_air_date != null)
            first_air_date.setText("Release Date: " + _first_air_date);

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
        String rt = "";
        try {
            if (jsonObject.get("episode_run_time") != null) {
                JSONArray runtimes = jsonObject.getJSONArray("episode_run_time");
                StringBuilder runtime = new StringBuilder();
                for (int i = 0; i < runtimes.length(); i++) {
                    runtime.append(runtimes.getInt(i) + (i == runtimes.length() - 1?"":", "));
                }
                rt = runtime.toString();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (runtime != null) {
            if (rt.equalsIgnoreCase("")) {
                runtime.setText("Runtime: Unknown");
            } else {
                runtime.setText("Runtime: " + rt + " minutes");
            }
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
    }
}
