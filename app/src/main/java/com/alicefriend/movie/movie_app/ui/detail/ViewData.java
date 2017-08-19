package com.alicefriend.movie.movie_app.ui.detail;

import com.alicefriend.movie.movie_app.domain.Movie;

/**
 * Created by choi on 2017. 8. 6..
 */

public class ViewData {
    public ViewData(Movie movie) {
        this.overview = movie.getOverview();
        this.rate = movie.getVoteAverage();
        this.year = movie.getReleaseDate();
        this.title = movie.getTitle();
    }

    public String overview;
    public String rate;
    public String year;
    public String title;
}
