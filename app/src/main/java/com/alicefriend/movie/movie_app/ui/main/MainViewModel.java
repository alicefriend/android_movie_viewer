package com.alicefriend.movie.movie_app.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.alicefriend.movie.movie_app.domain.Movie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by choi on 2017. 8. 5..
 */

public class MainViewModel extends ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();


    private MovieRepository movieRepo;

    private LiveData<List<Movie>> localMovies;
    private LiveData<List<Movie>> popularMovies;
    private LiveData<List<Movie>> topRateMovies;
    private ObservableField<Boolean> hasNetworkError = new ObservableField<>(false);

    @Inject
    public MainViewModel(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
        init();
    }

    public void init() {
        localMovies = movieRepo.loadFavoriteMovie();
        popularMovies = movieRepo.loadMoviesData("popular");
        topRateMovies = movieRepo.loadMoviesData("top_rated");
    }

    public LiveData<List<Movie>> getLocalMovies() {
        return localMovies;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<List<Movie>> getTopRateMovies() {
        return topRateMovies;
    }

    public ObservableField<Boolean> getHasNetworkError() {
        return hasNetworkError;
    }

}
