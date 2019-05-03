package com.inmovie.inmovie.TMDb.TV;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.TV.GetSimilarTVShows;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetSimilarTVShowsTest {
    @Test
    public void test() {
        try {
            JSONObject temp = new GetSimilarTVShows().execute(61923).get();
            assertTrue(temp.getInt("page") == 1 &&
                    temp.has("results") && temp.has("total_pages")
                    && temp.has("total_results"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
