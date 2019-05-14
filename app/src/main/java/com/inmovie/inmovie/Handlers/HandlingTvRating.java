package com.inmovie.inmovie.Handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.inmovie.inmovie.Adapters.Fragments.TvDetailsFragments.TvBasicInfoFragment;

public class HandlingTvRating extends Handler {
    private TvBasicInfoFragment basicInfoFragment;

    public HandlingTvRating(TvBasicInfoFragment fragment){
        basicInfoFragment = fragment;
    }

    @Override
    public void handleMessage(Message message) {
        Bundle ratingData = message.getData();
        if(ratingData != null){
            basicInfoFragment.setRatingSection(ratingData);
        }
    }
}
