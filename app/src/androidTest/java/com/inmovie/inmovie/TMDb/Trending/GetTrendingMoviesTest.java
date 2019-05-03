package com.inmovie.inmovie.TMDb.Trending;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Trending.GetTrendingMovies;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetTrendingMoviesTest {
    @Test
    public void test() {
        try {
            JSONObject trending = new GetTrendingMovies().execute().get();
            assertTrue(trending.has("results"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
