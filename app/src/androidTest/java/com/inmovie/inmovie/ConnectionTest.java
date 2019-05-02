package com.inmovie.inmovie;

import android.support.test.runner.AndroidJUnit4;

import com.inmovie.inmovie.model.api.CheckConnectivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConnectionTest {
    @Test
    public void testConnection() {
        try {
            assertEquals(7, new CheckConnectivity().execute().get().intValue());
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
