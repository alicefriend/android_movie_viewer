package com.alicefriend.movie.movie_app.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.alicefriend.movie.movie_app.db.AppDatabase;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;

import java.util.List;

/**
 * Created by choi on 2017. 8. 5..
 */

public class DetailViewModel extends AndroidViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private DetailRepository repository;
    private Movie movie;
    private MutableLiveData<List<Review>> reviews;
    private MutableLiveData<List<Trailer>> trailers;


    private Application application;
    private Boolean trailersLoadFailed;
    private Boolean reviewsLoadFailed;

    public DetailViewModel(Application application, Movie movie) {
        super(application);
        this.movie = movie;
        this.application = application;

        repository = new DetailRepository(movie);
        reviews = repository.getReviewsLiveData();
        trailers = repository.getTrailersLiveData();
    }

    public void insertMovie(final Movie movie) {
        new addAsyncTask(AppDatabase.getDatabase(application)).execute(movie);
    }

    private static class addAsyncTask extends AsyncTask<Movie, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            db.appDomainModel().insertMovie(params[0]);
            return null;
        }

    }

    public Boolean getTrailersLoadFailed() {
        return trailersLoadFailed;
    }

    public Boolean getReviewsLoadFailed() {
        return reviewsLoadFailed;
    }

    public MutableLiveData<List<Review>> getReviews() {
        return reviews;
    }

    public MutableLiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

}
