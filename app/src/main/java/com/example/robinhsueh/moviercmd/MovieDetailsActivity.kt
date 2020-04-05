package com.example.robinhsueh.moviercmd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

//these variables will be used as keys when we pass intent extras to MovieDetailsActivity
const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        val extras = intent.extras

        if(extras != null){
            populateDetails(extras)
        }else{
            finish()
        }
    }

    private fun populateDetails(extras: Bundle){
        val backdropPath: String? = extras.getString(MOVIE_BACKDROP)
        if (backdropPath != null){
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }
        val posterPath: String? = extras.getString(MOVIE_POSTER)
        if (posterPath != null){
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }
        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING,0f) /2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW,"")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }
}
