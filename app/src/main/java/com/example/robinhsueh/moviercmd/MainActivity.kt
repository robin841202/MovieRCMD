package com.example.robinhsueh.moviercmd

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private lateinit var popularMoviesPB: ProgressBar

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private lateinit var topRatedMoviesPB: ProgressBar

    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    private lateinit var upcomingMoviesPB: ProgressBar

    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    private var upcomingMoviesPage = 1

    private lateinit var pullToRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMovies = findViewById(R.id.popular_movies)
        popularMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf()){movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter
        popularMoviesPB = findViewById(R.id.popular_movies_PB)


        topRatedMovies = findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()){movie -> showMovieDetails(movie)}
        topRatedMovies.adapter = topRatedMoviesAdapter
        topRatedMoviesPB = findViewById(R.id.top_rated_movies_PB)


        upcomingMovies = findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MoviesAdapter(mutableListOf()){movie -> showMovieDetails(movie)}
        upcomingMovies.adapter = upcomingMoviesAdapter
        upcomingMoviesPB = findViewById(R.id.upcoming_movies_PB)


        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()

        pullToRefresh = findViewById(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener {
            getPopularMovies()
            Log.d("MainActivity","onRefreshPopular")
            getTopRatedMovies()
            Log.d("MainActivity","onRefreshTopRated")
            getUpcomingMovies()
            Log.d("MainActivity","onRefreshUpcoming")
            pullToRefresh.isRefreshing = false}

    }

    private fun getPopularMovies(){
        popularMoviesPB.visibility = View.VISIBLE
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    // callback when get popular movies
    private fun onPopularMoviesFetched(movies: List<Movie>){
        popularMoviesPB.visibility = View.GONE
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
        topRatedMoviesPB.visibility = View.VISIBLE
        MoviesRepository.getTopRatedMovies(
            topRatedMoviesPage,
            onSuccess = ::onTopRatedMoviesFetched,
            onError = ::onError
        )
    }

    // callback when get TopRated movies
    private fun onTopRatedMoviesFetched(movies: List<Movie>){
        topRatedMoviesPB.visibility = View.GONE
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
        upcomingMoviesPB.visibility = View.VISIBLE
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            onSuccess = ::onUpcomingMoviesFetched,
            onError = ::onError
        )
    }

    // callback when get upcoming movies
    private fun onUpcomingMoviesFetched(movies: List<Movie>){
        upcomingMoviesPB.visibility = View.GONE
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


    private fun showMovieDetails(movie: Movie){
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE,movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
        overridePendingTransition(R.anim.enter, R.anim.exit)
    }
}
