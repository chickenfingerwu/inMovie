package com.inmovie.inmovie.model.api.TMDb.TV.Seasons;

import android.os.AsyncTask;

import com.inmovie.inmovie.Adapters.TvEpisodeResultAdapter;
import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.TVclasses.Episode;
import com.inmovie.inmovie.model.api.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDetails extends AsyncTask<Integer, Void, JSONObject> {
    /**
     * Get details about a season of TV series
     * @param integers TV ID and season number
     * @return Details
     */
    private TvEpisodeResultAdapter episodeResultAdapter;
    private int showId;

    public GetDetails(TvEpisodeResultAdapter a, int id){
        showId = id;
        episodeResultAdapter = a;
    }

    @Override
    protected JSONObject doInBackground(Integer... integers) {
        JSONObject result = Network.getJSONObject("https://api.themoviedb.org/3/tv/" + integers[0] + "/season/" + integers[1] + "?api_key=" + BuildConfig.TMDb_API_key);

        return result;
    }

    @Override
    public void onPostExecute(JSONObject jsonObject){
        try {
            ArrayList<Episode> episodes = new ArrayList<>();
            JSONArray seasonEp = jsonObject.getJSONArray("episodes");
            for (int i = 0; i < seasonEp.length(); i++){
                JSONObject ep = seasonEp.getJSONObject(i);
                String thumbnail = ep.getString("still_path");
                String name = ep.getString("name");
                String overview = ep.getString("overview");
                int seasonNumber = ep.getInt("season_number");
                int epNumber = ep.getInt("episode_number");

                Episode e = new Episode();
                e.setTvID(showId);
                e.setSeasonNumber(seasonNumber);
                e.setEpisodeNumber(epNumber);
                e.setTitle(name);
                e.setPoster(thumbnail);
                e.setDescription(overview);
                episodes.add(e);
            }
            episodeResultAdapter.setEpisodesList(episodes, true);
            episodeResultAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
