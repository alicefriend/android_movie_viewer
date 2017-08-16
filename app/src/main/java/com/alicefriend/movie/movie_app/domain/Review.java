package com.alicefriend.movie.movie_app.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by choi on 2017. 8. 1..
 */

@Entity(foreignKeys=@ForeignKey(
        entity=Movie.class,
        parentColumns="id",
        childColumns="movieId"))
public class Review {

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    private String movieId;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("content")
    @Expose
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
