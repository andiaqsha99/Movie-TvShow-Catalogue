package com.kisaa.www.moviecatalogueapi.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kisaa.www.moviecatalogueapi.db.Favorite
import com.kisaa.www.moviecatalogueapi.db.FavoriteDao
import com.kisaa.www.moviecatalogueapi.db.FavoriteDatabase

class MovieFavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var favMovie: LiveData<List<Favorite>>
    private val database: FavoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()

    internal fun setFavoriteMovie() {
        favMovie = database.getFavoriteMovie()
    }

    internal fun getFavoriteMovie(): LiveData<List<Favorite>> {
        return favMovie
    }
}