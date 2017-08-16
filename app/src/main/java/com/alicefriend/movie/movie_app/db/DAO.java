package com.alicefriend.movie.movie_app.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by choi on 2017. 8. 5..
 */

@Dao
public interface DAO {

    @Query("select * from Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("select * from Review where id = :id")
    LiveData<List<Review>> getReviewsById(String id);

    @Query("select * from Trailer where id = :id")
    LiveData<List<Trailer>> getTrailersById(String id);

    @Insert(onConflict = REPLACE)
    void insertMovie(Movie movie);

    @Insert(onConflict = REPLACE)
    void insertReview(Review movie);

    @Insert(onConflict = REPLACE)
    void insertTrailer(Trailer movie);
}
