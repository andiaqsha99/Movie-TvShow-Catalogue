package com.kisaa.www.moviecatalogueapi.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kisaa.www.moviecatalogueapi.db.Favorite
import com.kisaa.www.moviecatalogueapi.db.FavoriteDao
import com.kisaa.www.moviecatalogueapi.db.FavoriteDatabase

class TvShowFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var favShow: LiveData<List<Favorite>>
    private val database: FavoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()

    internal fun setFavoriteShow() {
        favShow = database.getFavoriteTvShow()
    }

    internal fun getFavoriteShow(): LiveData<List<Favorite>> {
        return favShow
    }
}