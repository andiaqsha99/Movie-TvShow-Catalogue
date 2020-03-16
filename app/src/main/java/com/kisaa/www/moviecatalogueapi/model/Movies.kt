package com.kisaa.www.moviecatalogueapi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(
    var id: String? = null,
    var poster_path: String? = null,
    var title: String? = null,
    var overview: String? = null,
    var vote_average: String? = null,
    var backdrop_path: String? = null
) : Parcelable