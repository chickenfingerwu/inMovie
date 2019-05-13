package com.inmovie.inmovie.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WatchList {
    private File movies;
    private File tv;
    private ArrayList<Integer> moviesID;
    private ArrayList<Integer> tvID;

    /**
     * Default constructor
     * @param c Context
     */
    public WatchList(Context c) {
        movies = new File(c.getExternalCacheDir() + "movies.txt");
        tv = new File(c.getExternalCacheDir() + "tv.txt");
        moviesID = new ArrayList<>();
        tvID = new ArrayList<>();
        // Check if each file exists. If exists then read to ArrayList, else create a new file.
        if (movies.exists()) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(movies));
                String line;
                while((line = reader.readLine()) != null) {
                    moviesID.add(Integer.valueOf(line));
                }
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                movies.delete();
            }
        }
        else {
            try {
                movies.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (tv.exists()) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(tv));
                String line;
                while((line = reader.readLine()) != null) {
                    tvID.add(Integer.valueOf(line));
                }
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                tv.delete();
            }
        }
        else {
            try {
                tv.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateToFile(int option) {
        File file;
        ArrayList<Integer> list;
        switch (option) {
            case 0:
                file = movies;
                list = moviesID;
                break;
            case 1:
                file = tv;
                list = tvID;
                break;
            default:
                file = null;
                list = null;
        }
        if (file != null && list != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                for (int i = 0; i < list.size(); i++) {
                    writer.write(list.get(i) + "\n");
                }
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get ArrayList of movies' ID from Watch List
     * @return movies' ID list
     */
    public ArrayList<Integer> getMoviesID() {
        return moviesID;
    }

    /**
     * Get ArrayList of TV shows' ID from Watch List
     * @return TV shows' ID list
     */
    public ArrayList<Integer> getTvID() {
        return tvID;
    }

    /**
     * Insert movie ID into WatchList
     * @param ID TMDb ID of movie
     */
    public void addMovieID(int ID) {
        moviesID.add(ID);
        updateToFile(0);
    }

    /**
     * Insert tv ID into WatchList
     * @param ID TMDb ID of TV show
     */
    public void addTVID(int ID) {
        tvID.add(ID);
        updateToFile(1);
    }

    /**
     * Delete a movie from watch list. Also trim arrayList size.
     * @param position Position of movie in ArrayList. Position starts from 0.
     */
    public void deleteMovie(int position) {
        moviesID.remove(position);
        moviesID.trimToSize();
        updateToFile(0);
    }

    /**
     * Delete a movie from watch list. Also trim arrayList size.
     * @param id TMDb ID of movie in ArrayList.
     */
    public void deleteMovieByID(int id) {
        moviesID.remove(Integer.valueOf(id));
        moviesID.trimToSize();
        updateToFile(0);
    }

    /**
     * Delete a TV show from watch list. Also trim arrayList size.
     * @param position Position of TV show in ArrayList. Position starts from 0.
     */
    public void deleteTV(int position) {
        tvID.remove(position);
        tvID.trimToSize();
        updateToFile(1);
    }

    /**
     * Delete a TV show from watch list. Also trim arrayList size.
     * @param id TMDb ID of TV show in ArrayList.
     */
    public void deleteTVByID(int id) {
        tvID.remove(Integer.valueOf(id));
        tvID.trimToSize();
        updateToFile(1);
    }
}
