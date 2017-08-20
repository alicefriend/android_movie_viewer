package com.alicefriend.movie.movie_app.ui.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.domain.Review;
import com.alicefriend.movie.movie_app.domain.Trailer;

import java.util.List;

/**
 * Created by choi on 2017. 8. 5..
 */

public class DetailViewModel extends ViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private Movie movie;
    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();

    private Boolean trailersLoadFailed;
    private Boolean reviewsLoadFailed;

    private DetailRepository detailRepository;


    public DetailViewModel() {
    }

    public void init(Movie movie) {
        this.movie = movie;
        List<Review> reviews = detailRepository.getReviewer().getValue();
        List<Trailer> trailers = detailRepository.getTrailers().getValue();

    }

    public void insertMovie(final Movie movie) {
        detailRepository.addFavoriteMovie(movie);
    }



    public Boolean getTrailersLoadFailed() {
        return trailersLoadFailed;
    }

    public Boolean getReviewsLoadFailed() {
        return reviewsLoadFailed;
    }

    public MutableLiveData<List<Review>> getReviews() {
        return reviews;
    }

    public MutableLiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public void setTrailersLoadFailed(Boolean trailersLoadFailed) {
        this.trailersLoadFailed = trailersLoadFailed;
    }

    public void setReviewsLoadFailed(Boolean reviewsLoadFailed) {
        this.reviewsLoadFailed = reviewsLoadFailed;
    }
}
