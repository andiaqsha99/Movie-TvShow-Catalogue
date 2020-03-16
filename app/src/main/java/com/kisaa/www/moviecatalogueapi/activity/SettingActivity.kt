package com.kisaa.www.moviecatalogueapi.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.fragment.SettingPreferenceFragment

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.apply {
            title = resources.getString(R.string.setting)
            setDisplayHomeAsUpEnabled(true)
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.setting_activity, SettingPreferenceFragment()).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
