package com.inmovie.inmovie.model;

import org.json.simple.JSONObject;

import java.io.Serializable;

public class Actor implements Serializable {
    private String name;
    private String role;
    private String image;

    Actor(JSONObject actorInfo) {
        name = (String) actorInfo.get("name");
        role = (String) actorInfo.get("role");
        image = (String) actorInfo.get("image");
    }

    String getName() {
        return name;
    }

    String getRole() {
        return role;
    }

    String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return  "Name: " + name +
                "\nRole: " + role +
                "\nImage link:" + image;
    }
}
