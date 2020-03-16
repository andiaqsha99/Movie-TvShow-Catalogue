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
import com.kisaa.www.moviecatalogueapi.contentprovider.FavoriteProvider
import com.kisaa.www.moviecatalogueapi.db.Favorite
import com.kisaa.www.moviecatalogueapi.model.TvShows
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var tvShowDetailViewModel: TvShowDetailViewModel
    private lateinit var favorites: Favorite
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private var tvShows = TvShows()

    companion object {
        const val EXTRA_ID = "extra_id"
        private const val TV_SHOW = "tvshow"
        private const val KEY_DATA = "data"
        private const val STATE = "state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        setSupportActionBar(toolbar)

        val idShow = intent.getStringExtra(EXTRA_ID)

        tvShowDetailViewModel = ViewModelProvider(this)
            .get(TvShowDetailViewModel::class.java)
        showLoading(true)
        if (savedInstanceState == null) {
            tvShowDetailViewModel.setDetailTvShow(idShow)
            tvShowDetailViewModel.getMoviesById(idShow.toInt()).observe(this, Observer {
                if (it.isNotEmpty()) {
                    isFavorite = true
                }
            })
            tvShowDetailViewModel.getDetailTvShow().observe(this, Observer {
                tvShows = it
                setDetail(tvShows)
            })
        } else {
            tvShows = savedInstanceState.getParcelable(KEY_DATA)!!
            isFavorite = savedInstanceState.getBoolean(STATE)
            setDetail(tvShows)
        }
        showLoading(false)
    }

    private fun setDetail(tvShow: TvShows) {
        tv_tvshow_title.text = tvShow.name
        tv_tvshow_overview.text = if (tvShow.overview != "") {
            tvShow.overview
        } else {
            resources.getString(R.string.no_descrription)
        }
        grade_tv_show.text = tvShow.vote_average
        Picasso.get().load("https://image.tmdb.org/t/p/w185${tvShow.poster_path}")
            .into(img_tvshow)
        Picasso.get().load("https://image.tmdb.org/t/p/w342${tvShow.backdrop_path}")
            .into(img_backdrop)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = tvShow.name
        }
        favorites = Favorite(
            tvShow.id!!, tvShow.name, tvShow.overview,
            tvShow.vote_average, tvShow.poster_path, TV_SHOW
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
                    tvShowDetailViewModel.removeFromFavorite(favorites)
                    contentResolver?.notifyChange(FavoriteProvider.CONTENT_URI, null)
                    Toast.makeText(
                        this, resources.getString(R.string.remove_from_favorite),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    tvShowDetailViewModel.addToFavorite(favorites)
                    contentResolver?.notifyChange(FavoriteProvider.CONTENT_URI, null)
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
        outState.putParcelable(KEY_DATA, tvShows)
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
