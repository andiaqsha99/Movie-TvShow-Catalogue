package com.kisaa.www.favoriteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = pagerAdapter

        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }
}
