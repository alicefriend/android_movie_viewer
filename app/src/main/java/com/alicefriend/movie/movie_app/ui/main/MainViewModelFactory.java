package com.alicefriend.movie.movie_app.ui.main;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

/**
 * Created by choi on 2017. 8. 21..
 */

public class MainViewModelFactory implements ViewModelProvider.Factory{

    private final MovieRepository movieRepository;

    @Inject
    public MainViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MainViewModel create(Class modelClass) {
        return null;
    }
}
