package com.alicefriend.movie.movie_app.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.alicefriend.movie.movie_app.db.AppDatabase;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;
import com.alicefriend.movie.movie_app.network.RestService;
import com.alicefriend.movie.movie_app.network.RestServiceFactory;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choi on 2017. 8. 5..
 */

public class DetailViewModel extends AndroidViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private AppDatabase appDatabase;

    private Movie movie;
    private MutableLiveData<List<Review>> revies = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();

    private Observable<List<Review>> reviewObservable;
    private Observable<List<Trailer>> trailerObservable;

    private Boolean trailersLoadFailed;
    private Boolean reviewsLoadFailed;

    public DetailViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void init(Movie movie) {
        this.movie = movie;

        Gson gson = new Gson();

        reviewObservable = RestServiceFactory.getInstance().getReviews(movie.getId(), RestService.api_key)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(jObj -> jObj.get("results"))
                .map(results -> gson.fromJson(results, Review[].class))
                .map(reviewArr -> Arrays.asList(reviewArr));

        trailerObservable = RestServiceFactory.getInstance().getTrailers(movie.getId(), RestService.api_key)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(jObj -> jObj.get("results"))
                .map(trailers -> gson.fromJson(trailers, Trailer[].class))
                .map(trailerArr -> Arrays.asList(trailerArr));

        reviewObservable.subscribe(
                reviews -> {
                    revies.setValue(reviews);
                    reviewsLoadFailed = false;
                },
                t -> reviewsLoadFailed = true);

        trailerObservable.subscribe(
                trailers -> {
                    this.trailers.setValue(trailers);
                    trailersLoadFailed = false;
                },
                t -> trailersLoadFailed = true);
    }

    public void insertMovie(final Movie movie) {
        new addAsyncTask(appDatabase).execute(movie);
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

    public Observable<List<Review>> getReviewObservable() {
        return reviewObservable;
    }

    public Observable<List<Trailer>> getTrailerObservable() {
        return trailerObservable;
    }

    public Boolean getTrailersLoadFailed() {
        return trailersLoadFailed;
    }

    public Boolean getReviewsLoadFailed() {
        return reviewsLoadFailed;
    }

    public MutableLiveData<List<Review>> getRevies() {
        return revies;
    }

    public MutableLiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public void setTrailersLoadFailed(Boolean trailersLoadFailed) {
        this.trailersLoadFailed = trailersLoadFailed;
    }

    public void setReviewsLoadFailed(Boolean reviewsLoadFailed) {
        this.reviewsLoadFailed = reviewsLoadFailed;
    }
}
