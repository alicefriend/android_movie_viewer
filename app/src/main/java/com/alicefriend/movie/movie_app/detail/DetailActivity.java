package com.alicefriend.movie.movie_app.detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.databinding.ActivityDetailBinding;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.util.Utils;

/**
 * Created by choi on 2017. 8. 1..
 */

public class DetailActivity extends AppCompatActivity implements LifecycleOwner {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    private Movie mMovie;
    private ActivityDetailBinding mBinding;
    private DetailViewModel mDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("Movie")) {
                mMovie = (Movie)extras.getSerializable("Movie");
            }
        }

        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mDetailViewModel.init(mMovie);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mBinding.setMovie(mMovie);
        mBinding.setViewModel(mDetailViewModel);

        initTrailerView();
        initReviewView();

        Utils.posterImageLoad(mBinding.thumnail, mMovie);
        mBinding.favoriteButton.setOnClickListener(view -> {
            mDetailViewModel.insertMovie(mMovie);
            Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Lifecycle getLifecycle() {
        return mRegistry;
    }

    private void initTrailerView() {
        RecyclerView trailerView = mBinding.trailerList;
        TrailerAdapter trailerAdapter = new TrailerAdapter();
        mDetailViewModel.getTrailerObservable()
                .subscribe(
                        trailers -> {
                            trailerAdapter.setTrailers(trailers);
                            mDetailViewModel.setTrailersLoadFailed(false);
                        },
                        t ->mDetailViewModel.setTrailersLoadFailed(true));
        mDetailViewModel.getTrailers().observe(this, trailers -> trailerAdapter.setTrailers(trailers));
        trailerView.setLayoutManager(new LinearLayoutManager(this));
        trailerView.setAdapter(trailerAdapter);
    }

    private void initReviewView() {
        RecyclerView reviewView = mBinding.reviewList;
        ReviewAdapter reviewAdapter = new ReviewAdapter();
        mDetailViewModel.getReviewObservable()
                .subscribe(
                        reviews -> {
                            reviewAdapter.setReviews(reviews);
                            mDetailViewModel.setReviewsLoadFailed(false);
                        },
                        t ->mDetailViewModel.setReviewsLoadFailed(true));
        mDetailViewModel.getRevies().observe(this, reviews -> reviewAdapter.setReviews(reviews));
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewView.setAdapter(reviewAdapter);
    }

}
