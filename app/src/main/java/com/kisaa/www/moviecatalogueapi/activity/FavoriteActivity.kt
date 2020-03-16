package com.kisaa.www.moviecatalogueapi.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.adapter.FavoritePagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val pagerAdapter = FavoritePagerAdapter(this, supportFragmentManager)
        view_pager_favorite.adapter = pagerAdapter

        tab_favorite.setupWithViewPager(view_pager_favorite)

        supportActionBar?.apply {
            elevation = 0f
            title = "Favorite"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
