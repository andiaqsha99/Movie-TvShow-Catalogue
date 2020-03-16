package com.kisaa.www.moviecatalogueapi.network

import com.kisaa.www.moviecatalogueapi.BuildConfig
import com.kisaa.www.moviecatalogueapi.model.MovieResponse
import com.kisaa.www.moviecatalogueapi.model.Movies
import com.kisaa.www.moviecatalogueapi.model.TvResponse
import com.kisaa.www.moviecatalogueapi.model.TvShows
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getMovie(
        @Query("language") tag: String
    ): Call<MovieResponse>

    @GET("discover/tv?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getTvShow(
        @Query("language") tag: String
    ): Call<TvResponse>

    @GET("movie/{id}?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getDetailMovie(
        @Path("id") id: String,
        @Query("language") tag: String
    ): Call<Movies>

    @GET("tv/{id}?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getDetailTvShow(
        @Path("id") id: String,
        @Query("language") tag: String
    ): Call<TvShows>

    @GET("search/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getMovieSearch(
        @Query("language") tag: String,
        @Query("query") query: String?
    ): Call<MovieResponse>

    @GET("search/tv?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getTvShowSearch(
        @Query("language") tag: String,
        @Query("query") query: String?
    ): Call<TvResponse>

    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getReleaseMovie(
        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ): Call<MovieResponse>
}