package com.kisaa.www.moviecatalogueapi.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPagerAdapter =
            ViewPagerAdapter(
                this,
                supportFragmentManager
            )
        view_pager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.change_language -> {
                val setting = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(setting)
            }
            R.id.to_favorite -> {
                startActivity<FavoriteActivity>()
            }
            R.id.to_setting -> {
                startActivity<SettingActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
