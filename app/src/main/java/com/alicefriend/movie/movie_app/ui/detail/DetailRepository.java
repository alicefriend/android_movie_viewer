package com.alicefriend.movie.movie_app.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.alicefriend.movie.movie_app.db.MovieDao;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;
import com.alicefriend.movie.movie_app.network.RESTService;
import com.alicefriend.movie.movie_app.network.RESTServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 19..
 */

public class DetailRepository {


    private final MovieDao movieDao;
    private final Movie movie;

    public DetailRepository(MovieDao movieDao, Movie movie) {
        this.movieDao = movieDao;
        this.movie = movie;
    }

    public void addFavoriteMovie(Movie movie) {
        movieDao.insertMovie(movie);
    }

    public LiveData<List<Review>> getReviewer() {
        final MutableLiveData<List<Review>> reviewsLiveData = new MutableLiveData<>();
        RESTServiceFactory
                .getService()
                .getReviews(movie.getId(), RESTService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Review> reviews = Arrays.asList(new Gson().fromJson(results, Review[].class));
                        reviewsLiveData.setValue(reviews);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        reviewsLiveData.setValue(null);
                    }
                });
        return reviewsLiveData;
    }

    public LiveData<List<Trailer>> getTrailers() {
        final MutableLiveData<List<Trailer>> trailersLiveData = new MutableLiveData<>();
        RESTServiceFactory
                .getService()
                .getTrailers(movie.getId(), RESTService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Trailer> trailers= Arrays.asList(new Gson().fromJson(results, Trailer[].class));
                        trailersLiveData.setValue(trailers);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        trailersLiveData.setValue(null);
                    }
                });
        return trailersLiveData;
    }
}
