package com.inmovie.inmovie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.inmovie.inmovie.Activities.MoviesActivities.MovieDetails;

public class HandlingMovieRating extends Handler {
    private MovieDetails movieDetails;

    public HandlingMovieRating(MovieDetails activity) {
        movieDetails = activity;
    }

    @Override
    public void handleMessage(Message message) {
        Bundle ratingData = message.getData();
        if(ratingData != null){
            movieDetails.setRatingSection(ratingData);
        }
    }
}
