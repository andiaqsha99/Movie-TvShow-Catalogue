package com.kisaa.www.moviecatalogueapi.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.activity.TvShowDetailActivity
import com.kisaa.www.moviecatalogueapi.adapter.TvShowAdapter
import com.kisaa.www.moviecatalogueapi.model.TvShows
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {
    companion object {
        private const val KEY_DATA = "data_tv"
    }

    private lateinit var adapter: TvShowAdapter
    private var dataTV: ArrayList<TvShows> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TvShowAdapter {
            startActivity<TvShowDetailActivity>(TvShowDetailActivity.EXTRA_ID to it.id)
        }
        adapter.notifyDataSetChanged()

        rv_tvShow.layoutManager = LinearLayoutManager(activity)
        rv_tvShow.adapter = adapter
        showLoading(true)

        val tvShowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(TvShowViewModel::class.java)

        if (savedInstanceState == null) {
            tvShowViewModel.setTvShows()
            tvShowViewModel.getTvShows().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    adapter.setData(it)
                    adapter.notifyDataSetChanged()
                    dataTV = it
                    showLoading(false)
                }
            })
        } else {
            dataTV = savedInstanceState.getParcelableArrayList<TvShows>(KEY_DATA) as ArrayList
            adapter.setData(dataTV)
            adapter.notifyDataSetChanged()
            showLoading(false)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tvShowViewModel.setListSearch(newText)
                tvShowViewModel.getListSearch().observe(viewLifecycleOwner, Observer {
                    if (it != null) adapter.setData(it) else adapter.setData(dataTV)
                })

                return true
            }

        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            tv_progress_bar.visibility = View.VISIBLE
        } else {
            tv_progress_bar.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_DATA, dataTV)
        super.onSaveInstanceState(outState)
    }


}
