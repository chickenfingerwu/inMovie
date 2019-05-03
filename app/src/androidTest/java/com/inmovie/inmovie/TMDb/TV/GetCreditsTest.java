package com.inmovie.inmovie.TMDb.TV;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.TV.GetCredits;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetCreditsTest {
    @Test
    public void test() {
        try {
            JSONObject temp = new GetCredits().execute(61923).get();
            assertTrue(temp.getInt("id") == 61923 &&
                        temp.has("crew") && temp.has("cast"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
