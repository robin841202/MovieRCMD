package com.example.robinhsueh.moviercmd.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.robinhsueh.moviercmd.*

class SearchFragment : Fragment() {

    private lateinit var searchMovies: RecyclerView
    private lateinit var searchMoviesAdapter: MoviesAdapter
    private lateinit var searchMoviesLayoutMgr: GridLayoutManager
    private lateinit var searchView: SearchView
    private lateinit var searchMoviesPB: ProgressBar

    private var searchMoviesPage = 1
    private var keywords: String? = ""
    private var isFirst: Boolean = false
    private lateinit var pullToRefresh: SwipeRefreshLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        searchMovies = root.findViewById(R.id.search_movies)
        searchMoviesLayoutMgr = GridLayoutManager(context, 3)
        searchMovies.layoutManager = searchMoviesLayoutMgr
        searchMoviesAdapter = MoviesAdapter(mutableListOf()){movie -> showMovieDetails(movie) }
        searchMovies.adapter = searchMoviesAdapter
        searchMoviesPB = root.findViewById(R.id.search_movies_PB)

        searchView = root.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //getSearchMovies(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchMoviesPage = 1
                keywords = newText
                isFirst = true
                getSearchMovies()
                return false
            }
        })

        pullToRefresh = root.findViewById(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener {
            getSearchMovies()
            Log.d("SearchFragment","onRefreshSearch")
            pullToRefresh.isRefreshing = false}
        return root
    }

    private fun getSearchMovies(){
        searchMoviesPB.visibility = View.VISIBLE
        MoviesRepository.getSearchMovies(
            searchMoviesPage,
            keywords,
            onSuccess = ::onSearchMoviesFetched,
            onError = ::onError
        )
    }

    // callback when get popular movies
    private fun onSearchMoviesFetched(movies: List<Movie>){
        searchMoviesPB.visibility = View.GONE
        if (isFirst){
            searchMoviesAdapter.removeAllMovies()
        }
        searchMoviesAdapter.appendMovies(movies)
        attachSearchMoviesOnScrollListener()
        isFirst = false
        Log.d("SearchFragment", "Movies: $movies")
    }

    private fun attachSearchMoviesOnScrollListener(){
        searchMovies.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = searchMoviesLayoutMgr.itemCount //所有Item個數
                val visibleItemCount = searchMoviesLayoutMgr.childCount //一般螢幕大小大概為3個可看到的+左1右1未看到的
                val firstVisibleItem = searchMoviesLayoutMgr.findFirstVisibleItemPosition() //目前可見的最左邊Item index

                if(firstVisibleItem + visibleItemCount >= totalItemCount/2){
                    searchMovies.removeOnScrollListener(this)
                    searchMoviesPage++
                    getSearchMovies()
                }
            }
        })
    }

    // callback when failed to get movies
    private fun onError(){
        Toast.makeText(context,getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(movie: Movie){
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE,movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
    }
}
