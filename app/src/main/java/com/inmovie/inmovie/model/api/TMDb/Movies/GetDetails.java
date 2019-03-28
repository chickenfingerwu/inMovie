package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;

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

    public GetDetails(ArrayList<View> views) {
        // views[0]: movie's name
        // views[1]: movie's overview (a.k.a plot summary)
        // views[2]: backdrop (or banner)
        // views[3]: poster
        name = (TextView) views.get(0);
        overview = (TextView) views.get(1);
        backdrop = (ImageView) views.get(2);
        poster = (ImageView) views.get(3);
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + integers[0] + "?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US");
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

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        String _title = "UNDEFINED";
        String _overview = "UNDEFINED";
        try {
            _title = jsonObject.getString("title");
            _overview = jsonObject.getString("overview");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        name.setText(_title);
        overview.setText(_overview);
        backdrop.setImageBitmap(_backdrop);
        poster.setImageBitmap(_poster);
    }
}
