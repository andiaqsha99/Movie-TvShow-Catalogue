package com.kisaa.www.moviecatalogueapi.widget

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.db.Favorite
import com.kisaa.www.moviecatalogueapi.db.FavoriteDao
import com.kisaa.www.moviecatalogueapi.db.FavoriteDatabase

class StackRemoteViewFactory(application: Application, private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val database: FavoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()
    private lateinit var listFavorite: List<Favorite>

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        listFavorite = database.getAllFavorite()
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(
            context.packageName,
            R.layout.widget_item
        )
        val bitmap: Bitmap? = Glide.with(context)
            .asBitmap()
            .load("https://image.tmdb.org/t/p/w342${listFavorite[position].posterPath}")
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.image_widget, bitmap)

        Log.d("widget", "getViewAt")

        return rv
    }

    override fun getCount(): Int = listFavorite.size

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}