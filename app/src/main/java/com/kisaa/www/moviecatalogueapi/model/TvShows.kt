package com.kisaa.www.moviecatalogueapi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShows(
    var id: String? = null,
    var poster_path: String? = null,
    var name: String? = null,
    var overview: String? = null,
    var vote_average: String? = null,
    var backdrop_path: String? = null
) : Parcelable