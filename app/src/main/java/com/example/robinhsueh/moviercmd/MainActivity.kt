package com.example.robinhsueh.moviercmd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    private var upcomingMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMovies = findViewById(R.id.popular_movies)
        popularMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf())
        popularMovies.adapter = popularMoviesAdapter


        topRatedMovies = findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf())
        topRatedMovies.adapter = topRatedMoviesAdapter


        upcomingMovies = findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MoviesAdapter(mutableListOf())
        upcomingMovies.adapter = upcomingMoviesAdapter


        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()

    }

    private fun getPopularMovies(){
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    // callback when get popular movies
    private fun onPopularMoviesFetched(movies: List<Movie>){
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
        Log.d("MainActivity", "Movies: $movies")
    }

    private fun attachPopularMoviesOnScrollListener(){
        popularMovies.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount //所有Item個數
                val visibleItemCount = popularMoviesLayoutMgr.childCount //一般螢幕大小大概為3個可看到的+左1右1未看到的
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition() //目前可見的最左邊Item index

                if(firstVisibleItem + visibleItemCount >= totalItemCount/2){
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }


    private fun getTopRatedMovies(){
        MoviesRepository.getTopRatedMovies(
            topRatedMoviesPage,
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError
        )
    }

    // callback when get TopRated movies
    private fun onTopRatedMoviesFetched(movies: List<Movie>){
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
        Log.d("MainActivity", "Movies: $movies")
    }

    private fun attachTopRatedMoviesOnScrollListener(){
        topRatedMovies.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount //所有Item個數
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount //一般螢幕大小大概為3個可看到的+左1右1未看到的
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition() //目前可見的最左邊Item index

                if(firstVisibleItem + visibleItemCount >= totalItemCount/2){
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }


    private fun getUpcomingMovies(){
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            onSuccess = ::onUpcomingMoviesFetched,
            onError = ::onError
        )
    }

    // callback when get upcoming movies
    private fun onUpcomingMoviesFetched(movies: List<Movie>){
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
        Log.d("MainActivity", "Movies: $movies")
    }

    private fun attachUpcomingMoviesOnScrollListener(){
        upcomingMovies.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount //所有Item個數
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount //一般螢幕大小大概為3個可看到的+左1右1未看到的
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition() //目前可見的最左邊Item index

                if(firstVisibleItem + visibleItemCount >= totalItemCount/2){
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }

    // callback when failed to get movies
    private fun onError(){
        Toast.makeText(this,getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}
