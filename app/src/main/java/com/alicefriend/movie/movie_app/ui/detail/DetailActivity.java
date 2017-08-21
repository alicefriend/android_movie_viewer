package com.alicefriend.movie.movie_app.ui.detail;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.databinding.ActivityDetailBinding;
import com.alicefriend.movie.movie_app.domain.Movie;
import com.alicefriend.movie.movie_app.util.Utils;

/**
 * Created by choi on 2017. 8. 1..
 */

public class DetailActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    private Movie movie;
    private ActivityDetailBinding binding;
    private DetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("Movie")) {
                movie = (Movie)extras.getSerializable("Movie");
            }
        }

        ViewModelProvider.Factory factory = new DetailViewModelFactory(getApplication(), movie);
        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setMovie(movie);
        binding.setViewModel(viewModel);

        initAppBar();
        initTrailerView();
        initReviewView();

        Utils.posterImageLoad(binding.thumnail, movie);
        binding.addFavoriteBtn.setOnClickListener(view -> {
            viewModel.addFavorite();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.detailCoordinatorLayout),
                    R.string.favorite_added, Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.undo_string, v -> {
                viewModel.deleteFavorite();
            });
            snackbar.show();
        });

        binding.deleteFavoriteBtn.setOnClickListener(view -> {
            viewModel.deleteFavorite();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.detailCoordinatorLayout),
                    R.string.favorite_deleted, Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_refresh:
                viewModel.loadData();
        }
        return super.onOptionsItemSelected(item);
    }


    private void initTrailerView() {
        RecyclerView trailerView;
        trailerView = binding.trailerList;
        TrailerAdapter trailerAdapter = new TrailerAdapter();
        viewModel.getTrailersLiveData().observe(this, trailers -> trailerAdapter.setTrailers(trailers));
        trailerView.setLayoutManager(new LinearLayoutManager(this));
        trailerView.setAdapter(trailerAdapter);
    }

    private void initReviewView() {
        RecyclerView reviewView;
        reviewView = binding.reviewList;
        ReviewAdapter reviewAdapter = new ReviewAdapter();
        viewModel.getReviewsLiveData().observe(this, reviews -> reviewAdapter.setReviews(reviews));
        reviewView.setLayoutManager(new LinearLayoutManager(this));
        reviewView.setAdapter(reviewAdapter);

    }

    private void initAppBar() {
        final Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }
}
