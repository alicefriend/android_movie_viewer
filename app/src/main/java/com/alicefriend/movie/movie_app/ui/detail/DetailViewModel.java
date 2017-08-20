package com.alicefriend.movie.movie_app.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.alicefriend.movie.movie_app.db.AppDatabase;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;
import com.alicefriend.movie.movie_app.network.RestService;
import com.alicefriend.movie.movie_app.network.RestServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 5..
 */

public class DetailViewModel extends AndroidViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private AppDatabase appDatabase;

    private Movie movie;
    private MutableLiveData<List<Review>> reviews;
    private MutableLiveData<List<Trailer>> trailers;

    Observable<List<Review>> reviewsObservable;

    private Boolean trailersLoadFailed;
    private Boolean reviewsLoadFailed;

    public DetailViewModel(Application application, Movie movie) {
        super(application);
        this.movie = movie;

        reviews = new MutableLiveData<>();
        trailers = new MutableLiveData<>();


        RestServiceFactory.getInstance().getReviews(movie.getId(), RestService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        Log.d(TAG, "onResponse: " + results.toString());
                        List<Review> reviewList = Arrays.asList(new Gson().fromJson(results, Review[].class));
                        reviewsObservable = Observable.just(reviewList);
                        reviews.setValue(reviewList);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d(TAG, "onFailure: reviews");
                    }
                });

        RestServiceFactory.getInstance().getTrailers(movie.getId(), RestService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        Log.d(TAG, "onResponse: " + results.toString());
                        List<Trailer> trailerList = Arrays.asList(new Gson().fromJson(results, Trailer[].class));
                        trailers.setValue(trailerList);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d(TAG, "onFailure: trailers");
                    }
                });
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
