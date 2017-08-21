package com.alicefriend.movie.movie_app.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
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
    private Application application;

    private Movie movie;
    private MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailersLiveData = new MutableLiveData<>();
    private ObservableField<Boolean> loadTrailersFailed = new ObservableField<>(false);
    private ObservableField<Boolean> loadReviewsFailed = new ObservableField<>(false);

    public DetailViewModel(Application application, Movie movie) {
        super(application);
        this.movie = movie;
        this.application = application;
        repository = new DetailRepository(movie);
        repository.reviews(reviewsLiveData, loadReviewsFailed);
        repository.trailers(trailersLiveData, loadTrailersFailed);
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

    public MutableLiveData<List<Review>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public MutableLiveData<List<Trailer>> getTrailersLiveData() {
        return trailersLiveData;
    }

    public ObservableField<Boolean> getLoadTrailersFailed() {
        return loadTrailersFailed;
    }

    public ObservableField<Boolean> getLoadReviewsFailed() {
        return loadReviewsFailed;
    }
}
