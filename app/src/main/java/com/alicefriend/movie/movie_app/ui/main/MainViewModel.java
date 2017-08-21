package com.alicefriend.movie.movie_app.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

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

    private LiveData<List<Movie>> storedMovies;
    private MutableLiveData<List<Movie>> popularMovies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> topRatedMovies = new MutableLiveData<>();
    private ObservableField<Boolean> loadPopularFailed = new ObservableField<>(false);
    private ObservableField<Boolean> loadTopRatedFailed = new ObservableField<>(false);

    private MainRepository repository;

    public MainViewModel(Application application) {
        super(application);
        RestService restService = RestServiceFactory.getInstance();
        repository = new MainRepository(restService, MovieDao.getInstance(application));
        initModelData();
    }


    public void initModelData() {
        repository.getMovies("popular", popularMovies, loadPopularFailed);
        repository.getMovies("top_rated", topRatedMovies, loadTopRatedFailed);
        storedMovies = repository.getStoredMovie();
    }

    public LiveData<List<Movie>> getStoredMovies() {
        return storedMovies;
    }

    public MutableLiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public MutableLiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public ObservableField<Boolean> getLoadPopularFailed() {
        return loadPopularFailed;
    }

    public ObservableField<Boolean> getLoadTopRatedFailed() {
        return loadTopRatedFailed;
    }
}
