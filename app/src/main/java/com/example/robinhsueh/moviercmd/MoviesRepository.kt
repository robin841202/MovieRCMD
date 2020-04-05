package com.example.robinhsueh.moviercmd

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {
    private val api: Api
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit){
        val call = api.getPopularMovies(page=page)
        call.enqueue(object: Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        onSuccess.invoke(responseBody.movies)
                    }else{
                        onError.invoke()
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                onError.invoke()
            }
        })
    }

    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit){
        val call = api.getTopRatedMovies(page=page)
        call.enqueue(object: Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        onSuccess.invoke(responseBody.movies)
                    }else{
                        onError.invoke()
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                onError.invoke()
            }
        })
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit){
        val call = api.getUpcomingMovies(page=page)
        call.enqueue(object: Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        onSuccess.invoke(responseBody.movies)
                    }else{
                        onError.invoke()
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                onError.invoke()
            }
        })
    }

}