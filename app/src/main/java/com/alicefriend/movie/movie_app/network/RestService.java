package com.alicefriend.movie.movie_app.network;

import com.alicefriend.movie.movie_app.BuildConfig;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by choi on 2017. 8. 21..
 */

public interface RestService {

    public final static String api_key_param = "api_key";
    public final static String api_key = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    @GET("movie/{sort}")
    Call<JsonObject> getMovie(@Path("sort") String order, @Query(api_key_param) String key);

    @GET("movie/{movie_id}/reviews")
    Call<JsonObject> getReviews(@Path("movie_id") String movie_id, @Query(api_key_param) String api_key);

    @GET("movie/{movie_id}/videos")
    Call<JsonObject> getTrailers(@Path("movie_id") String movie_id, @Query(api_key_param) String api_key);
}
