package com.kisaa.www.moviecatalogueapi.fragment

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kisaa.www.moviecatalogueapi.model.TvResponse
import com.kisaa.www.moviecatalogueapi.model.TvShows
import com.kisaa.www.moviecatalogueapi.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowViewModel : ViewModel() {

    private val listTvShow = MutableLiveData<ArrayList<TvShows>>()
    private val listSearch = MutableLiveData<ArrayList<TvShows>>()
    private var languageTag = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        .toLanguageTag()

    internal fun setTvShows() {
        val listItem = ArrayList<TvShows>()
        if (languageTag == "in-ID") {
            languageTag = "id-ID"
        }

        NetworkConfig().api().getTvShow(languageTag).enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                val items = response.body()
                for (tvShow in items?.results!!.iterator()) {
                    listItem.add(tvShow)
                }
                listTvShow.postValue(listItem)
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                listTvShow.postValue(null)
            }
        })
    }

    internal fun getTvShows(): LiveData<ArrayList<TvShows>> {
        return listTvShow
    }

    internal fun setListSearch(query: String?) {
        val listResult = ArrayList<TvShows>()
        if (languageTag == "in-ID") {
            languageTag = "id-ID"
        }

        NetworkConfig().api().getTvShowSearch(languageTag, query)
            .enqueue(object : Callback<TvResponse> {
                override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                    listSearch.postValue(null)
                }

                override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                    val result = response.body()
                    if (result != null) {
                        for (tvShow in result.results!!.iterator()) {
                            listResult.add(tvShow)
                        }
                        listSearch.postValue(listResult)
                    } else {
                        listSearch.postValue(null)
                    }
                }
            })
    }

    internal fun getListSearch(): LiveData<ArrayList<TvShows>> {
        return listSearch
    }
}