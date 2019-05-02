package com.inmovie.inmovie.model.api.TMDb.Movies;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

import javax.net.ssl.HttpsURLConnection;

public class GetCredits extends AsyncTask<Integer, Void, JSONObject> {

    private LinearLayout cast;
    private Context layoutContext;

    public GetCredits(LinearLayout c){
        cast = c;
        layoutContext = c.getContext();
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
        try{
            LayoutInflater inflater = (LayoutInflater) layoutContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            JSONArray castJSON = jsonObject.getJSONArray("cast");
            String name = "";
            String role = "";
            for(int i = 0; i < castJSON.length(); i++){
                JSONObject actor = castJSON.getJSONObject(i);
                name = actor.getString("name");
                role = actor.getString("character");

                LinearLayout nameRole = (LinearLayout) inflater.inflate(R.layout.name_character_field, null);

                TextView actorN = (TextView) nameRole.getChildAt(0);
                actorN.setText(name);

                TextView actorR = (TextView) nameRole.getChildAt(1);
                actorR.setText(role);

                cast.addView(nameRole, cast.getChildCount() - 1);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
