package com.kisaa.www.moviecatalogueapi.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.kisaa.www.moviecatalogueapi.db.FavoriteDao
import com.kisaa.www.moviecatalogueapi.db.FavoriteDatabase

class FavoriteProvider : ContentProvider() {

    private lateinit var database: FavoriteDao

    companion object {
        private const val FAVORITE = 1
        private const val TABLE_NAME = "table_favorite"
        private const val AUTHORITY = "com.kisaa.www.moviecatalogueapi"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private const val SCHEME = "content"
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        init {
            uriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME,
                FAVORITE
            )
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        database = FavoriteDatabase.getInstance(context!!).favoriteDao()

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?

        when (uriMatcher.match(uri)) {
            FAVORITE -> cursor = database.getFavoriteCursor()
            else -> cursor = null
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
