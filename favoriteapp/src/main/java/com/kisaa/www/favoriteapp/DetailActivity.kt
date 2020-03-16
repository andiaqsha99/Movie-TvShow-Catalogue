package com.kisaa.www.favoriteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val favorite = intent.getParcelableExtra<Favorite>("bundle")
        tv_movie_title.text = favorite.title
        grade_movie.text = favorite.voteAverage
        tv_movie_overview.text = if (favorite.overview != "") {
            favorite.overview
        } else {
            resources.getString(R.string.no_description)
        }

        Picasso.get().load("https://image.tmdb.org/t/p/w185${favorite.posterPath}")
            .into(img_movie)
    }
}
