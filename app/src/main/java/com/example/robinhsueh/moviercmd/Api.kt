package com.example.robinhsueh.moviercmd

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key")
        apiKey: String = "45573754115aa294605178ed2769f617",
        @Query("page")
        page: Int,
        @Query("language")
        language: String = "zh-TW",
        @Query("region")
        region: String = "TW"
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = "45573754115aa294605178ed2769f617",
        @Query("page")
        page: Int,
        @Query("language")
        language: String = "zh-TW",
        @Query("region")
        region: String = "TW"
    ): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key")
        apiKey: String = "45573754115aa294605178ed2769f617",
        @Query("page")
        page: Int,
        @Query("language")
        language: String = "zh-TW",
        @Query("region")
        region: String = "TW"
    ): Call<MoviesResponse>

    @GET("search/movie")
    fun getSearchMovies(
        @Query("api_key")
        apiKey: String = "45573754115aa294605178ed2769f617",
        @Query("page")
        page: Int,
        @Query("query")
        keywords: String?
    ): Call<MoviesResponse>
}