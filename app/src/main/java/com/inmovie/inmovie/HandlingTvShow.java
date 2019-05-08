package com.inmovie.inmovie;

import android.os.Handler;
import android.os.Message;

import com.inmovie.inmovie.Activities.TvActivities.TvDetails;
import com.inmovie.inmovie.TVclasses.TvShow;

public class HandlingTvShow extends Handler {
    private TvDetails tvDetails;

    public HandlingTvShow(TvDetails activity){
        tvDetails = activity;
    }

    @Override
    public void handleMessage(Message message){
        TvShow show = (TvShow) message.getData().get("details");
        if(show!=null) {
            tvDetails.setShowBanner(show);
            tvDetails.setTabs(show);
            tvDetails.setTrailerTextColor(show);
        }
    }

}
