package com.inmovie.inmovie.TMDb.Movies;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Movies.GetCredits;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetCreditsTest {
    @Test
    public void testGetCredits() {
        try {
            JSONObject result = new GetCredits().execute(24428).get();
            assertTrue("true", result.getInt("id") == 24428 && result.has("cast") && result.has("crew"));
        }
        catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalid() {
        try {
            JSONObject result = new GetCredits().execute(-1).get();
            assertTrue("true", !result.has("id"));
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
