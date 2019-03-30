package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;

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

public class GetCredits extends AsyncTask<Integer, Void, JSONObject> {
    private ArrayList<TextView> actorsName;
    private ArrayList<TextView> roles;
    private ArrayList<ImageView> images;
    private ArrayList<Bitmap> _images;

    public GetCredits(ArrayList<TextView> actorsName, ArrayList<TextView> roles, ArrayList<ImageView> images) {
        this.actorsName = actorsName;
        this.roles = roles;
        this.images = images;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = new JSONObject();
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + integers[0] + "/credits?api_key=" + BuildConfig.TMDb_API_key + "&language=en-US");
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
    protected void onPostExecute(JSONObject jsonObject) {
        JSONArray cast = null;

        try {
            cast = jsonObject.getJSONArray("cast");
            for (int i = 0; i < cast.length(); i++) {

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
