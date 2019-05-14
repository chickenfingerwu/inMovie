package com.inmovie.inmovie.Handlers;

import android.os.Handler;
import android.os.Message;

import com.inmovie.inmovie.Activities.TvActivities.TvDetails;
import com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments.TvBasicInfoFragment;
import com.inmovie.inmovie.MovieTvClasses.TvClasses.TvShow;

public class HandlingTvShow extends Handler {
    private TvDetails tvDetails;
    private TvBasicInfoFragment basicInfo;

    public HandlingTvShow(TvDetails activity){
        tvDetails = activity;
    }

    public HandlingTvShow(TvBasicInfoFragment fragment) { basicInfo = fragment;}

    public HandlingTvShow(TvDetails activity, TvBasicInfoFragment fragment) {
        tvDetails = activity;
        basicInfo = fragment;
    }

    @Override
    public void handleMessage(Message message){
        TvShow show = (TvShow) message.getData().get("details");
        if(show!=null) {
            if (tvDetails != null) {
                tvDetails.setShowBanner(show);
                tvDetails.setTabs(show);
            }
            if (basicInfo != null) {
                basicInfo.setTrailerTextColor(show);
            }
        }
    }

}
