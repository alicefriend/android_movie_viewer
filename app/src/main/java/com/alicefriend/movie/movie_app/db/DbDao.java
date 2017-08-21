package com.alicefriend.movie.movie_app.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alicefriend.movie.movie_app.domain.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 2017. 8. 21..
 */

public class DbDao {

    private DbDao(Context context) {
        database = new DbHelper(context).getWritableDatabase();
    }

    private SQLiteDatabase database;
    private MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
    private static DbDao instance;

    public static DbDao getInstance(Context context) {
        if(instance == null) {
            instance = new DbDao(context);
        }
        return instance;
    }

    public LiveData<List<Movie>> getAllMovies() {
        Cursor cursor = database.query(
                DbContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        List<Movie> movieList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(DbContract.MovieEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(DbContract.MovieEntry.COLUMN_TITLE));
            String voteAverage = cursor.getString(cursor.getColumnIndex(DbContract.MovieEntry.COLUMN_VOTE_AVERAGE));
            String releaseDate = cursor.getString(cursor.getColumnIndex(DbContract.MovieEntry.COLUMN_RELEASE_DATE));
            String posterPath = cursor.getString(cursor.getColumnIndex(DbContract.MovieEntry.COLUMN_POSTER_PATH));
            String overview = cursor.getString(cursor.getColumnIndex(DbContract.MovieEntry.COLUMN_OVERVIEW));
            movieList.add(new Movie(id, posterPath, overview, releaseDate, title, voteAverage));
        }
        movieLiveData.postValue(movieList);
        return movieLiveData;
    }

    public void insertMovie(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(DbContract.MovieEntry._ID, movie.getId());
        cv.put(DbContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        cv.put(DbContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        cv.put(DbContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        cv.put(DbContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        cv.put(DbContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        database.insert(DbContract.MovieEntry.TABLE_NAME, null, cv);
        getAllMovies();
    }

    public void deleteMovie(Movie movie) {
        database.delete(DbContract.MovieEntry.TABLE_NAME,
                DbContract.MovieEntry._ID + "=?",
                new String[]{movie.getId()});
        getAllMovies();
    }
}
