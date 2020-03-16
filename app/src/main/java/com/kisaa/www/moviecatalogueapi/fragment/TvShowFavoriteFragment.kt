package com.kisaa.www.moviecatalogueapi.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.activity.TvShowDetailActivity
import com.kisaa.www.moviecatalogueapi.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class TvShowFavoriteFragment : Fragment() {

    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteAdapter {
            startActivity<TvShowDetailActivity>(TvShowDetailActivity.EXTRA_ID to it.movieId)
        }

        rv_tvShow_favorite.layoutManager = LinearLayoutManager(activity)
        rv_tvShow_favorite.adapter = adapter

        val tvShowFavoriteViewModel = ViewModelProvider(this)
            .get(TvShowFavoriteViewModel::class.java)
        tvShowFavoriteViewModel.setFavoriteShow()
        tvShowFavoriteViewModel.getFavoriteShow().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }
}
