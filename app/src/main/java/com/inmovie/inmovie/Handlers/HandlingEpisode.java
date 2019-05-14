package com.inmovie.inmovie.Handlers;

import android.os.Handler;
import android.os.Message;

import com.inmovie.inmovie.Activities.TvActivities.EpisodeDetails;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.Episode;

public class HandlingEpisode extends Handler {
    private EpisodeDetails episodeDetails;

    public HandlingEpisode(EpisodeDetails activity){
        episodeDetails = activity;
    }

    @Override
    public void handleMessage(Message message) {
        Episode episode = (Episode) message.getData().get("details");
        if (episode != null) {
            episodeDetails.setTrailerTextColor(episode);
            episodeDetails.setEpisodeBanner(episode);
        }
    }

}
