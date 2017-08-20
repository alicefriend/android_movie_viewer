package com.alicefriend.movie.movie_app.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alicefriend.movie.movie_app.domain.Movie;

/**
 * Created by choi on 2017. 8. 5..
 */

@Database(entities = {Movie.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao appDomainModel();
}
