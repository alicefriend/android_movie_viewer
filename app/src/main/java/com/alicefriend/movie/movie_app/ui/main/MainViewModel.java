package com.alicefriend.movie.movie_app.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.alicefriend.movie.movie_app.db.AppDatabase;
import com.alicefriend.movie.movie_app.db.MovieDao;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.network.RestService;
import com.alicefriend.movie.movie_app.network.RestServiceFactory;

import java.util.List;

/**
 * Created by choi on 2017. 8. 5..
 */

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    //private AppDatabase appDatabase;

    private LiveData<List<Movie>> storedMovies;
    private MutableLiveData<List<Movie>> popularMovies;
    private MutableLiveData<List<Movie>> topRateMovies;
    private ObservableField<Boolean> hasNetworkError = new ObservableField<>(false);

    private MainRepository repository;

    public MainViewModel(Application application) {
        super(application);


        RestService restService = RestServiceFactory.getInstance();
        MovieDao movieDao = AppDatabase.getDatabase(getApplication()).appDomainModel();
        repository = new MainRepository(restService, movieDao);
        init();
    }


    public void init() {
        // Load data
        popularMovies = repository.getMovies("popular");
        topRateMovies = repository.getMovies("top_rated");
        storedMovies = repository.getStoredMovie();

        if (popularMovies.getValue() == null || topRateMovies.getValue() == null) {
            hasNetworkError.set(true);
        }

        /*
        RestServiceFactory.getInstance().getMovies("popular", RestService.api_key)
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

        RestServiceFactory.getInstance().getMovies("top_rated", RestService.api_key)
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
                */
    }

    public LiveData<List<Movie>> getStoredMovies() {
        return storedMovies;
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
