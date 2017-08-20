package com.alicefriend.movie.movie_app.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alicefriend.movie.movie_app.domain.Movie;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by choi on 2017. 8. 5..
 */

@Dao
public interface MovieDao {
    @Query("select * from Movie")
    LiveData<List<Movie>> getAllMovies();

    @Insert(onConflict = REPLACE)
    void insertMovie(Movie movie);
}
