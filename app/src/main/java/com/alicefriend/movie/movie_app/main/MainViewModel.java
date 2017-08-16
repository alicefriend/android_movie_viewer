package com.alicefriend.movie.movie_app.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.alicefriend.movie.movie_app.db.AppDatabase;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.network.RestApiHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by choi on 2017. 8. 5..
 */

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private AppDatabase appDatabase;

    private LiveData<List<Movie>> localMovies;
    private MutableLiveData<List<Movie>> popularMovies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> topRateMovies = new MutableLiveData<>();

    private ObservableField<Boolean> hasNetworkError = new ObservableField<>(false);

    public MainViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        localMovies = appDatabase.appDomainModel().getAllMovies();
        loadData();
    }

    public void loadData() {
        RestApiHelper.service.getMoviesByPopular(RestApiHelper.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Movie> movies = Arrays.asList(new Gson().fromJson(results, Movie[].class));
                        popularMovies.setValue(movies);
                        hasNetworkError.set(false);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        hasNetworkError.set(true);
                    }
                });

        RestApiHelper.service.getMoviesByRating(RestApiHelper.api_key)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonElement results = response.body().get("results");
                        List<Movie> movies = Arrays.asList(new Gson().fromJson(results, Movie[].class));
                        topRateMovies.setValue(movies);
                        hasNetworkError.set(false);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        hasNetworkError.set(true);
                    }
                });
    }

    public LiveData<List<Movie>> getLocalMovies() {
        return localMovies;
    }

    public MutableLiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public MutableLiveData<List<Movie>> getTopRateMovies() {
        return topRateMovies;
    }

    public ObservableField<Boolean> getHasNetworkError() {
        return hasNetworkError;
    }

}
