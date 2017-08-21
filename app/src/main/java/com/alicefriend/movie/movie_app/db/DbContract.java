package com.alicefriend.movie.movie_app.db;

import android.provider.BaseColumns;

/**
 * Created by choi on 2017. 8. 21..
 */

public class DbContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
    }
}
