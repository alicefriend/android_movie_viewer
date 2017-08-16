package com.alicefriend.movie.movie_app.network;

import com.alicefriend.movie.movie_app.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by choi on 2017. 8. 2..
 */

public class RestApiHelper {

    private final static String TAG = RestApiHelper.class.getSimpleName();

    private final static String api_key_param = "api_key";
    public final static String api_key = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    public final static RestService service;

    static {
        Gson gson = new GsonBuilder().create();
        service = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RestService.class);
    }

    public interface RestService {
        @GET("movie/popular")
        Call<JsonObject> getMoviesByPopular(@Query(api_key_param) String api_key);

        @GET("movie/top_rated")
        Call<JsonObject> getMoviesByRating(@Query(api_key_param) String api_key);

        @GET("movie/{sort}")
        Call<JsonObject> getMovie(@Path("sort") String order, @Query(api_key_param) String key);

        @GET("movie/{movie_id}/reviews")
        Observable<JsonObject> getReviews(@Path("movie_id") String movie_id, @Query(api_key_param) String api_key);

        @GET("movie/{movie_id}/videos")
        Observable<JsonObject> getTrailers(@Path("movie_id") String movie_id, @Query(api_key_param) String api_key);
    }
}
