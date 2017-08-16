package com.alicefriend.movie.movie_app.util;

import android.widget.ImageView;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.bumptech.glide.Glide;

/**
 * Created by choi on 2017. 8. 10..
 */

public class Utils {
    public static void posterImageLoad(ImageView imageView, Movie movie) {
        final String baseUrl = "http://image.tmdb.org/t/p/w185/";
        Glide.with(imageView.getContext())
                .load(baseUrl + movie.getPosterPath())
                .dontAnimate()
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(imageView);
    }
}
