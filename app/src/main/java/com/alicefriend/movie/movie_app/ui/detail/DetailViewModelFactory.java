package com.alicefriend.movie.movie_app.ui.detail;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import com.alicefriend.movie.movie_app.domain.Movie;

/**
 * Created by choi on 2017. 8. 21..
 */

class DetailViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final Movie movie;

    public DetailViewModelFactory(Application application, Movie movie) {
        this.application = application;
        this.movie = movie;
    }

    @Override
    public DetailViewModel create(Class modelClass) {
        return new DetailViewModel(application, movie);
    }
}
