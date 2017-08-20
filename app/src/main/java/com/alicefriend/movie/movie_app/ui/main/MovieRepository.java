package com.alicefriend.movie.movie_app.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.alicefriend.movie.movie_app.db.MovieDao;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.network.RESTService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 19..
 */

@Singleton
public class MovieRepository {

    private final RESTService restService;
    private final MovieDao movieDao;

    @Inject
    public MovieRepository(RESTService restService, MovieDao movieDao) {
        this.restService = restService;
        this.movieDao = movieDao;
    }

    public LiveData<List<Movie>> loadMoviesData(String criteria) {
        final MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
        restService.getMovie(criteria, RESTService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Movie> movies = Arrays.asList(new Gson().fromJson(results, Movie[].class));
                        moviesLiveData.setValue(movies);
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        moviesLiveData.setValue(null);
                    }
                });
        return moviesLiveData;
    }

    public LiveData<List<Movie>> loadFavoriteMovie() {
        return movieDao.getAllMovies();
    }
}
