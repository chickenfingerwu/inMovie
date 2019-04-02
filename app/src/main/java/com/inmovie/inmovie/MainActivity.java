package com.inmovie.inmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*TheTVDB theTVDB = TheTVDB.getInstance();
        Series test = new Series(282994);*/
        //System.out.println(test);
        //System.out.println(new Series(theTVDB.seriesInfoByID(282994), theTVDB.actorsInfoByID(282994), theTVDB.getPostersByID(282994)));
        //System.out.println(TMDb.getInstance().searchMoviesByName("the avengers"));
        //System.out.println(TMDb.getInstance().searchMoviesByName("oggyandthecockroaches"));
    }
}
