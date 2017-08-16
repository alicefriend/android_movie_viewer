package com.alicefriend.movie.movie_app.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choi on 2017. 8. 1..
 */

public class Trailer {

    public Trailer(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

}
