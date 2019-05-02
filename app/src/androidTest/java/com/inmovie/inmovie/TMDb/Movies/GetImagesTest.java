package com.inmovie.inmovie.TMDb.Movies;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Movies.GetImages;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetImagesTest {
    @Test
    public void test() {
        try {
            JSONObject result = new GetImages().execute(24428).get();
            assertTrue(result.getInt("id") == 24428 &&
                                result.has("backdrops") && result.has("posters"));
        }
        catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
