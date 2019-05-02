package com.inmovie.inmovie;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.OMDb.GetRating;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class OMDbTest {
    @Test
    public void testOMDb() {
        JSONObject rating = GetRating.getRatingByID("tt2758770");
        assertTrue(rating.has("score") && rating.has("votes"));
    }
}
