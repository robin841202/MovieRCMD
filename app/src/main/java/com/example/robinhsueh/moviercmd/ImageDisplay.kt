package com.example.robinhsueh.moviercmd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter

const val IMAGE_DISPLAY = "extra_image_display"

class ImageDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)
        val image_display: ImageView = findViewById(R.id.image_display)
        val extras = intent.extras
        if (extras != null){
            val imagePath: String? = extras.getString(IMAGE_DISPLAY)
            if(imagePath != null){
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original$imagePath")
                    .transform(FitCenter())
                    .into(image_display)
            }
        }else{
            finish()
        }
    }
}
