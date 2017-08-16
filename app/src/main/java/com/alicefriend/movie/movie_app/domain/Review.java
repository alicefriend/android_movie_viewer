package com.alicefriend.movie.movie_app.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by choi on 2017. 8. 1..
 */

public class Review {

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return this.author;
    }

}
