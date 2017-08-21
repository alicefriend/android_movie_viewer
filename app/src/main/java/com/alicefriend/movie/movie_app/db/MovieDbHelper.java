package com.alicefriend.movie.movie_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alicefriend.movie.movie_app.db.MovieDbContract.MovieEntry;

/**
 * Created by choi on 2017. 8. 21..
 */

public class MovieDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 10;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIELIST_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " TEXT PRIMARY KEY, " +
                MovieEntry.COLUMN_TITLE+ " TEXT, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " TEXT, " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieEntry.COLUMN_POSTER_PATH+ " TEXT" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
