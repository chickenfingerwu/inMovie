package com.inmovie.inmovie.TMDb.Movies;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Movies.GetVideos;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetVideosTest {
    @Test
    public void test() {
        try {
            JSONArray result = new GetVideos().execute(24428).get();
            assertTrue(result.getJSONObject(0).has("id") &&
                    result.getJSONObject(0).has("link"));
        }
        catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
