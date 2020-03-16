package com.kisaa.www.favoriteapp

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    val movieId: String,
    val title: String?,
    val overview: String?,
    val voteAverage: String?,
    val posterPath: String?,
    val category: String?
) : Parcelable {
    companion object {
        private const val TABLE_NAME = "table_favorite"
        const val MOVIE_ID = "movie_id"
        const val TITLE = "title"
        const val OVERVIEW = "overview"
        const val VOTE_AVERAGE = "vote_average"
        const val POSTER_PATH = "poster_path"
        const val CATEGORY = "category"
        private const val AUTHORITY = "com.kisaa.www.moviecatalogueapi"
        private const val SCHEME = "content"
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}