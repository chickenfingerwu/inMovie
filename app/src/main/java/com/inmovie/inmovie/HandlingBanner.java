package com.inmovie.inmovie;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.inmovie.inmovie.Activities.TvDetails;
import com.inmovie.inmovie.TVclasses.TvShow;

public class HandlingBanner extends Handler {
    private TvDetails tvDetails;

    public HandlingBanner(TvDetails activity){
        tvDetails = activity;
    }

    @Override
    public void handleMessage(Message message){
        TvShow show = (TvShow) message.getData().get("details");
        tvDetails.setShowBanner(show);
    }

}
