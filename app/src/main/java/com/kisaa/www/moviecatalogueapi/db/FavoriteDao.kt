package com.kisaa.www.moviecatalogueapi.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM table_favorite WHERE category = 'movie'")
    fun getFavoriteMovie(): LiveData<List<Favorite>>

    @Query("SELECT * FROM table_favorite WHERE category = 'tvshow'")
    fun getFavoriteTvShow(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFromFavorite(favorite: Favorite)

    @Query("SELECT * FROM table_favorite WHERE movie_id = :id")
    fun checkMovieById(id: Int): LiveData<List<Favorite>>

    @Query("SELECT * FROM table_favorite")
    fun getAllFavorite(): List<Favorite>

    @Query("SELECT * FROM table_favorite")
    fun getFavoriteCursor(): Cursor
}