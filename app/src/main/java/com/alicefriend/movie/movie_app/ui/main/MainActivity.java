package com.alicefriend.movie.movie_app.ui.main;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alicefriend.movie.movie_app.R;
import com.alicefriend.movie.movie_app.databinding.ActivityMainBinding;
import com.alicefriend.movie.movie_app.ui.settings.SettingsActivity;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    private MovieAdapter mPopularAdapter = new MovieAdapter();
    private MovieAdapter mTopRateAdapter = new MovieAdapter();
    private MovieAdapter mFavoriteAdapter = new MovieAdapter();

    private GridLayoutManager mLayoutManager;

    Preference<String> orderPref;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mainViewModel);

        initAppBar();
        initRecyclerView();
        initPreferenceSetting();

        mainViewModel.getPopularMovies().observe(this, movies -> mPopularAdapter.setMovies(movies));
        mainViewModel.getTopRatedMovies().observe(this, movies -> mTopRateAdapter.setMovies(movies));
        mainViewModel.getStoredMovies().observe(this, movies -> mFavoriteAdapter.setMovies(movies));

        binding.fab.setOnClickListener(v -> {
            Snackbar.make(findViewById(R.id.mainCoordinatorLayout),
                    R.string.not_yet_implemented, Snackbar.LENGTH_SHORT).show();
        });

        binding.refresh.setOnClickListener(v -> {
            mainViewModel.initModelData();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        this.menu = menu;
        hideOption(R.id.action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_info) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {

        int num_column;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            num_column = 4;
        } else {
            num_column = 2;
        }
        mLayoutManager = new GridLayoutManager(this, num_column);

        binding.movieGridView.setLayoutManager(mLayoutManager);
        binding.movieGridView.setHasFixedSize(true);
    }

    private void initPreferenceSetting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);
        orderPref= rxPreferences.getString(getString(R.string.pref_order_key));
        orderPref.asObservable().subscribe(order -> {
            final String popular = getString(R.string.pref_order_popular_value);
            final String topRate = getString(R.string.pref_order_rate_value);
            final String favorite = getString(R.string.pref_order_favorite_value);

            if (order.equals(popular)) {
                binding.movieGridView.setAdapter(mPopularAdapter);
            } else if (order.equals(topRate)) {
                binding.movieGridView.setAdapter(mTopRateAdapter);
            } else if (order.equals(favorite)) {
                binding.movieGridView.setAdapter(mFavoriteAdapter);
            } else {
                Log.d(TAG, "initPreferenceSetting: " + "'orderBy' doesn't match.");
            }
        });
    }

    private void initAppBar() {
        final Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

}