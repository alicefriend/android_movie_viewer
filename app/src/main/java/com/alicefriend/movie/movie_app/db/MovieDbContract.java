package com.alicefriend.movie.movie_app.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by choi on 2017. 8. 21..
 */

public class MovieDbContract {

    public static final String AUTHORITY = "com.alicefriend.movie.movie_app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
    }
}
