package com.inmovie.inmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.inmovie.inmovie.model.api.TMDb;
import com.inmovie.inmovie.model.api.TheTVDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TheTVDB theTVDB = new TheTVDB();
        System.out.println(theTVDB.getPostersByID(282994));
        //System.out.println(new Series(theTVDB.seriesInfoByID(282994), theTVDB.actorsInfoByID(282994), theTVDB.getPostersByID(282994)));
        System.out.println(new TMDb().searchMoviesByName("the avengers"));
        System.out.println(new TMDb().searchMoviesByName("oggyandthecockroaches"));
    }
}
