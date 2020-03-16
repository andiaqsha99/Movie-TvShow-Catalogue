package com.kisaa.www.moviecatalogueapi.fragment

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kisaa.www.moviecatalogueapi.model.MovieResponse
import com.kisaa.www.moviecatalogueapi.model.Movies
import com.kisaa.www.moviecatalogueapi.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val listMovie = MutableLiveData<ArrayList<Movies>>()
    private val listSearch = MutableLiveData<ArrayList<Movies>>()
    private var languageTag = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        .toLanguageTag()

    internal fun setMovies() {
        val listItems = ArrayList<Movies>()
        if (languageTag == "in-ID") {
            languageTag = "id-ID"
        }
        NetworkConfig().api().getMovie(languageTag).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val items = response.body()
                for (movies in items!!.results!!.iterator()) {
                    listItems.add(movies)
                }
                listMovie.postValue(listItems)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                listMovie.postValue(null)
            }
        })
    }

    internal fun getMovies(): LiveData<ArrayList<Movies>> {
        return listMovie
    }

    internal fun setListSearch(query: String?) {
        val listResult = ArrayList<Movies>()
        if (languageTag == "in-ID") {
            languageTag = "id-ID"
        }
        NetworkConfig().api().getMovieSearch(languageTag, query)
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    listSearch.postValue(null)
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    val result = response.body()
                    if (result != null) {
                        for (movies in result.results!!.iterator()) {
                            listResult.add(movies)
                        }
                        listSearch.postValue(listResult)
                    } else {
                        listSearch.postValue(null)
                    }
                }

            })
    }

    internal fun getListSearch(): LiveData<ArrayList<Movies>> {
        return listSearch
    }
}