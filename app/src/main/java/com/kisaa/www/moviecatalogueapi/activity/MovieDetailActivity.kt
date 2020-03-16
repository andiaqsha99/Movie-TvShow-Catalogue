package com.kisaa.www.moviecatalogueapi.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.contentprovider.FavoriteProvider.Companion.CONTENT_URI
import com.kisaa.www.moviecatalogueapi.db.Favorite
import com.kisaa.www.moviecatalogueapi.model.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var favorites: Favorite
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private var movies: Movies = Movies()

    companion object {
        const val EXTRA_ID = "extra_id"
        private const val MOVIE = "movie"
        private const val KEY_DATA = "data"
        private const val STATE = "state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        val idMovie = intent.getStringExtra(EXTRA_ID)
        movieDetailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        showLoading(true)
        if (savedInstanceState == null) {
            movieDetailViewModel.setDetailMovie(idMovie)
            movieDetailViewModel.getMoviesById(idMovie.toInt()).observe(this, Observer {
                if (it.isNotEmpty()) {
                    isFavorite = true
                }
            })
            movieDetailViewModel.getDetailMovie().observe(this, Observer {
                movies = it
                setDetail(movies)
            })
        } else {
            movies = savedInstanceState.getParcelable(KEY_DATA)!!
            isFavorite = savedInstanceState.getBoolean(STATE)
            setDetail(movies)
        }
        showLoading(false)
    }

    private fun setDetail(movie: Movies) {
        tv_movie_title.text = movie.title
        tv_movie_overview.text = if (movie.overview != "") {
            movie.overview
        } else {
            resources.getString(R.string.no_descrription)
        }
        grade_movie.text = movie.vote_average
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w185${movie.poster_path}")
            .into(img_movie)
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w342${movie.backdrop_path}")
            .into(img_backdrop)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = movie.title
        }
        favorites = Favorite(
            movie.id!!, movie.title, movie.overview,
            movie.vote_average, movie.poster_path, MOVIE
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.favorite_icon -> {
                if (isFavorite) {
                    movieDetailViewModel.removeFromFavorite(favorites)
                    contentResolver?.notifyChange(CONTENT_URI, null)
                    Toast.makeText(
                        this, resources.getString(R.string.remove_from_favorite),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    movieDetailViewModel.addToFavorite(favorites)
                    contentResolver?.notifyChange(CONTENT_URI, null)
                    Toast.makeText(
                        this, resources.getString(R.string.add_to_favorite),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                isFavorite = !isFavorite
                setFavoriteIcon()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavoriteIcon() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_DATA, movies)
        outState.putBoolean(STATE, isFavorite)
        super.onSaveInstanceState(outState)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            detail_progress_bar.visibility = View.VISIBLE
        } else {
            detail_progress_bar.visibility = View.GONE
        }
    }
}
