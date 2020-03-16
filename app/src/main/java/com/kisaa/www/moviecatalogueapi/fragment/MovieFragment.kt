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
import com.kisaa.www.moviecatalogueapi.activity.MovieDetailActivity
import com.kisaa.www.moviecatalogueapi.adapter.MovieAdapter
import com.kisaa.www.moviecatalogueapi.model.Movies
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    companion object {
        private const val KEY_DATA = "data"
    }

    private lateinit var adapter: MovieAdapter
    private var dataMovie: ArrayList<Movies> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MovieAdapter {
            startActivity<MovieDetailActivity>(MovieDetailActivity.EXTRA_ID to it.id)
        }
        adapter.notifyDataSetChanged()

        rv_movie.layoutManager = LinearLayoutManager(activity)
        rv_movie.adapter = adapter

        showLoading(true)
        val movieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieViewModel::class.java)

        if (savedInstanceState == null) {
            movieViewModel.setMovies()
            movieViewModel.getMovies().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    adapter.setData(it)
                    dataMovie = it
                    showLoading(false)
                }
            })
        } else {
            dataMovie = savedInstanceState.getParcelableArrayList<Movies>(KEY_DATA) as ArrayList
            adapter.setData(dataMovie)
            adapter.notifyDataSetChanged()
            showLoading(false)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieViewModel.setListSearch(newText)
                movieViewModel.getListSearch().observe(viewLifecycleOwner, Observer {
                    if (it != null) adapter.setData(it) else adapter.setData(dataMovie)
                })
                return true
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            m_progress_bar.visibility = View.VISIBLE
        } else {
            m_progress_bar.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_DATA, dataMovie)
        super.onSaveInstanceState(outState)
    }
}
