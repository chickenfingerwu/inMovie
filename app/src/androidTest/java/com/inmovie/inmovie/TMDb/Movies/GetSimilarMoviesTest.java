package com.inmovie.inmovie.TMDb.Movies;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Movies.GetSimilarMovies;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetSimilarMoviesTest {
    @Test
    public void test() {
        try {
            JSONObject result = new GetSimilarMovies().execute(24428).get();
            assertTrue(result.getInt("id") == 24428 &&
                    result.getInt("page") == 1 &&
                    result.has("results") && result.has("total_pages") &&
                    result.has("total_results"));
        }
        catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
