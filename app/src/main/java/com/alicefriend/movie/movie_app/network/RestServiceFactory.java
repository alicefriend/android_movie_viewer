package com.alicefriend.movie.movie_app.network;

import com.alicefriend.movie.movie_app.BuildConfig;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by choi on 2017. 8. 21..
 */

public class RestServiceFactory {

    private final static String TAG = RestServiceFactory.class.getSimpleName();

    private final static RestService service = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_END_POINT)
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RestService.class);

    private RestServiceFactory() {}

    public static RestService getInstance() {
        return service;
    }
}
