package com.inmovie.inmovie.TMDb.TV;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.TV.GetImages;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetImagesTest {
    @Test
    public void test() {
        try {
            JSONObject temp = new GetImages().execute(61923).get();
            assertTrue(temp.getInt("id") == 61923 &&
                        temp.has("backdrops") && temp.has("posters"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
