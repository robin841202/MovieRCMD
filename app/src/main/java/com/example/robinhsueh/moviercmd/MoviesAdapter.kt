package com.example.robinhsueh.moviercmd

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit  //an higher-order function that will be called when a movie is clicked.
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        Log.d("MoviesAdapter", "this.movies add before: ${this.movies.size}")
        this.movies.addAll(movies)
        Log.d("MoviesAdapter", "this.movies add after: ${this.movies.size}")
        Log.d("MoviesAdapter", "movies size: ${movies.size}")
        notifyItemRangeInserted(this.movies.size, movies.size-1)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            itemView.setOnClickListener{onMovieClick.invoke(movie)}  //Invoke onMovieClick when a movie is clicked.
        }
    }
}