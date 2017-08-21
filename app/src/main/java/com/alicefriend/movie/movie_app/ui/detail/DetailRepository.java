package com.alicefriend.movie.movie_app.ui.detail;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 21..
 */

public class DetailRepository {

    private final Movie movie;

    public DetailRepository(Movie movie) {
        this.movie = movie;
    }

    public void reviews(MutableLiveData<List<Review>> reviewsLiveData, ObservableField<Boolean> loadFailed) {
        RestServiceFactory.getInstance().getReviews(movie.getId(), RestService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Review> reviewList = Arrays.asList(new Gson().fromJson(results, Review[].class));
                        reviewsLiveData.setValue(reviewList);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loadFailed.set(true);
                    }
                });
    }

    public void trailers(MutableLiveData<List<Trailer>> trailersLiveData, ObservableField<Boolean> loadFailed) {
        RestServiceFactory.getInstance().getTrailers(movie.getId(), RestService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Trailer> trailerList = Arrays.asList(new Gson().fromJson(results, Trailer[].class));
                        trailersLiveData.setValue(trailerList);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loadFailed.set(true);
                    }
                });
    }
}
