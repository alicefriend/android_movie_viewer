package com.alicefriend.movie.movie_app.network;

import com.alicefriend.movie.movie_app.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by choi on 2017. 8. 2..
 */

public class RESTServiceFactory {

    private final static String TAG = RESTServiceFactory.class.getSimpleName();

    private RESTServiceFactory() {}

    public static RESTService getService() {
        RESTService service;
        Gson gson = new GsonBuilder().create();
        service = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RESTService.class);
        return service;
    }
}
