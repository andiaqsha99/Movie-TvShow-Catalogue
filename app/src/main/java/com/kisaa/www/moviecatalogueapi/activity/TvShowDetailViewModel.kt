package com.kisaa.www.moviecatalogueapi.activity

import android.app.Application
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kisaa.www.moviecatalogueapi.db.Favorite
import com.kisaa.www.moviecatalogueapi.db.FavoriteDao
import com.kisaa.www.moviecatalogueapi.db.FavoriteDatabase
import com.kisaa.www.moviecatalogueapi.model.TvShows
import com.kisaa.www.moviecatalogueapi.network.NetworkConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movies = MutableLiveData<TvShows>()
    private lateinit var favMovie: LiveData<List<Favorite>>
    private var languageTag = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        .toLanguageTag()
    private val database: FavoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()

    internal fun setDetailTvShow(idShow: String) {
        if (languageTag == "in-ID") {
            languageTag = "id-ID"
        }
        NetworkConfig().api().getDetailTvShow(idShow, languageTag).enqueue(object :
            Callback<TvShows> {
            override fun onFailure(call: Call<TvShows>, t: Throwable) {
                movies.postValue(TvShows())
            }

            override fun onResponse(call: Call<TvShows>, response: Response<TvShows>) {
                val tvShow = response.body()
                movies.postValue(tvShow)
            }

        })
    }

    internal fun getDetailTvShow(): LiveData<TvShows> {
        return movies
    }

    internal fun addToFavorite(data: Favorite) {
        viewModelScope.launch {
            database.addFavorite(data)
        }
    }

    internal fun removeFromFavorite(data: Favorite) {
        viewModelScope.launch {
            database.deleteFromFavorite(data)
        }
    }

    internal fun getMoviesById(id: Int): LiveData<List<Favorite>> {
        favMovie = database.checkMovieById(id)
        return favMovie
    }
}