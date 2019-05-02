package com.inmovie.inmovie.TMDb.Search;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.TMDb.Search.SearchMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SearchMoviesTest {
    @Test
    public void test() {
        try {
            JSONArray result = new SearchMovies().execute("avengers").get();
            assertTrue(result.getJSONObject(0).has("id") &&
                    result.getJSONObject(0).has("title") &&
                    result.getJSONObject(0).has("poster_path"));
        }
        catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
