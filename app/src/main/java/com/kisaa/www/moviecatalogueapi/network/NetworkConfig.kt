package com.kisaa.www.moviecatalogueapi.network

import com.kisaa.www.moviecatalogueapi.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig {

    private fun getNetwork(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun api(): ApiService {
        return getNetwork().create(ApiService::class.java)
    }
}