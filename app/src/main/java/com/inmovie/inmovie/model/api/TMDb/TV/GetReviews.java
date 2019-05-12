package com.inmovie.inmovie.model.api.TMDb.TV;

import android.os.AsyncTask;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONObject;

public class GetReviews extends AsyncTask<Integer, Void, JSONObject> {
    public GetReviews() {}

    // TODO: Create another constructor with Views

    /**
     * Get reviews
     * @param integers Movie's ID and page
     * @return JSONObject
     */
    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/tv/" + integers[0] + "/reviews?api_key=" + BuildConfig.TMDb_API_key  + "&page=" + integers[1]);

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        // TODO: Set something here
    }
}
