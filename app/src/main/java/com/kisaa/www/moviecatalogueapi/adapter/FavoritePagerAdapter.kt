package com.kisaa.www.moviecatalogueapi.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.fragment.MovieFavoriteFragment
import com.kisaa.www.moviecatalogueapi.fragment.TvShowFavoriteFragment

class FavoritePagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TITLE = intArrayOf(
        R.string.movie_title,
        R.string.tvshow_title
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                MovieFavoriteFragment()
            1 -> fragment =
                TvShowFavoriteFragment()
        }

        return fragment as Fragment
    }

    override fun getCount() = 2

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TITLE[position])
    }
}