package com.inmovie.inmovie.TMDb.Search;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Search.SearchTV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SearchTVTest {
    @Test
    public void test() {
        try {
            JSONArray result = new SearchTV().execute("star vs").get();
            JSONObject temp = result.getJSONObject(0);
            assertTrue(temp.has("id") &&
                    temp.has("name") &&
                    temp.has("poster_path"));
        }
        catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
