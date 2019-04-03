package com.inmovie.inmovie;

import java.util.ArrayList;

public class Actor {
    private String actorName;
    private int age;
    private ArrayList<Entertainment> filmography;

    public Actor(String name){
        actorName = name;
    }

    public Actor(String name, int a){
        actorName = name;
        age = a;
    }

    //add to his/her filmography
    public void addFilmography(Entertainment m){
        filmography.add(m);
    }

    public int getAge() {
        return age;
    }

    public String getActorName() {
        return actorName;
    }


}
