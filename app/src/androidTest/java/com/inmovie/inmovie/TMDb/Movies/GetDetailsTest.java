package com.inmovie.inmovie.TMDb.Movies;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Movies.GetDetails;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetDetailsTest {
    @Test
    public void testGetDetails() {
        try {
            JSONObject result = new GetDetails().execute(24428).get();
            assertTrue("true", result.getInt("id") == 24428 &&
                    result.getString("title").equalsIgnoreCase("The Avengers") &&
                    result.getString("status").equalsIgnoreCase("Released"));
        }
        catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
