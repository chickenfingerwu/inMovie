package com.inmovie.inmovie.TMDb.TV;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.TV.GetVideos;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetVideosTest {
    @Test
    public void test() {
        try {
            JSONArray temp = new GetVideos().execute(61923).get();
            assertTrue(temp.length() > 0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
