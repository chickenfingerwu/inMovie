package com.inmovie.inmovie.TMDb.TV;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.TV.GetDetails;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class GetDetailsTest {
    @Test
    public void test() {
        try {
            JSONObject result = new GetDetails().execute(61923).get();
            assertTrue(result.getInt("id") == 61923 &&
                    result.getString("name").equalsIgnoreCase("Star vs. the Forces of Evil"));
        }
        catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalid() {
        try {
            JSONObject result = new GetDetails().execute(-1).get();
            assertTrue(!result.has("id"));
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
