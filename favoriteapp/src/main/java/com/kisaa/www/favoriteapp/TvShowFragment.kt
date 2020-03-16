package com.kisaa.www.favoriteapp


import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisaa.www.favoriteapp.Favorite.Companion.CONTENT_URI
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteAdapter() {
            startActivity<DetailActivity>("bundle" to it)
        }
        rv_tvShow_favorite.layoutManager = LinearLayoutManager(activity)
        rv_tvShow_favorite.adapter = adapter

        val handlerThread = HandlerThread("DataTvShowObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadFavoriteAsync()
            }
        }

        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)
        loadFavoriteAsync()
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = activity?.contentResolver?.query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                ) as Cursor
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favorites = deferredFavorite.await().filter { it.category == "tvshow" }
            adapter.setData(favorites)
        }
    }
}
