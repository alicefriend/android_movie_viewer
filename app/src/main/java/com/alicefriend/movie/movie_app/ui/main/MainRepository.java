package com.alicefriend.movie.movie_app.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.alicefriend.movie.movie_app.db.MovieDao;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.network.RestService;
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

public class MainRepository {

    private final RestService service;
    private final MovieDao dao;

    public MainRepository(RestService service, MovieDao dao) {
        this.service = service;
        this.dao = dao;
    }

    public MutableLiveData<List<Movie>> getMovies(String criteria) {
        MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

        service.getMovie(criteria, RestService.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Movie> movieList = Arrays.asList(new Gson().fromJson(results, Movie[].class));
                        movies.setValue(movieList);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        movies.setValue(null);
                    }
                });
        return movies;
    }

    public LiveData<List<Movie>> getStoredMovie() {
        return dao.getAllMovies();
    }
}
