package com.alicefriend.movie.movie_app.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.databinding.ObservableField;
import android.net.Uri;

import com.alicefriend.movie.movie_app.domain.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 2017. 8. 21..
 */

public class MovieDao {

    private static MovieDao instance;
    private final ContentResolver resolver;
    private MutableLiveData<List<Movie>> movieLiveData;

    private MovieDao(Context context) {
        movieLiveData = new MutableLiveData<>();
        resolver = context.getContentResolver();
    }

    public static MovieDao getInstance(Context context) {
        if(instance == null) {
            instance = new MovieDao(context);
        }
        return instance;
    }

    public void addFavorite(Movie movie){
        ContentValues cv = new ContentValues();
        cv.put(MovieDbContract.MovieEntry._ID, movie.getId());
        cv.put(MovieDbContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieDbContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        cv.put(MovieDbContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieDbContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        cv.put(MovieDbContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        resolver.insert(MovieDbContract.MovieEntry.CONTENT_URI, cv);
        getAllMovies();
    }

    public void deleteFavorite(Movie movie) {
        Uri baseUri = MovieDbContract.MovieEntry.CONTENT_URI;
        Uri uri = baseUri.buildUpon().appendPath(movie.getId()).build();
        resolver.delete(uri, null, null);
        getAllMovies();
    }

    public LiveData<List<Movie>> getAllMovies() {

        Cursor cursor = resolver.query(MovieDbContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        List<Movie> movieList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_TITLE));
            String voteAverage = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_VOTE_AVERAGE));
            String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_RELEASE_DATE));
            String posterPath = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_POSTER_PATH));
            String overview = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_OVERVIEW));
            movieList.add(new Movie(id, posterPath, overview, releaseDate, title, voteAverage));
        }
        movieLiveData.postValue(movieList);
        return movieLiveData;
    }

    public ObservableField<Boolean> isFavorite(Movie movie) {
        Cursor cursor = resolver.query(MovieDbContract.MovieEntry.CONTENT_URI,
                null,
                MovieDbContract.MovieEntry._ID + "=?",
                new String[]{ movie.getId() },
                null);
        ObservableField<Boolean> isFavorite = new ObservableField<>();
        if(cursor.moveToNext()){
            isFavorite.set(true);
        } else {
            isFavorite.set(false);
        }
        return isFavorite;
    }
}
