package com.alicefriend.movie.movie_app.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;

/**
 * Created by choi on 2017. 8. 5..
 */

@Database(entities = {Movie.class, Review.class, Trailer.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "movie_db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract DAO appDomainModel();

}