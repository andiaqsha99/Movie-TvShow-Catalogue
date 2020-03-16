package com.kisaa.www.favoriteapp

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(favCursor: Cursor): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()

        while (favCursor.moveToNext()) {
            val id = favCursor.getString(favCursor.getColumnIndexOrThrow(Favorite.MOVIE_ID))
            val title = favCursor.getString(favCursor.getColumnIndexOrThrow(Favorite.TITLE))
            val category = favCursor.getString(favCursor.getColumnIndexOrThrow(Favorite.CATEGORY))
            val overview = favCursor.getString(favCursor.getColumnIndexOrThrow(Favorite.OVERVIEW))
            val posterPath =
                favCursor.getString(favCursor.getColumnIndexOrThrow(Favorite.POSTER_PATH))
            val voteAverage =
                favCursor.getString(favCursor.getColumnIndexOrThrow(Favorite.VOTE_AVERAGE))

            favList.add(Favorite(id, title, overview, voteAverage, posterPath, category))
        }

        return favList
    }
}