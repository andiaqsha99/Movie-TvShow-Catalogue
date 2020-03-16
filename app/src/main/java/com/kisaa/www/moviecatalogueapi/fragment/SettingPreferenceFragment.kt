package com.kisaa.www.moviecatalogueapi.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.notification.ReleaseTodayReceiver
import com.kisaa.www.moviecatalogueapi.notification.ReminderReceiver

class SettingPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var RELEASE: String
    private lateinit var releasePreference: SwitchPreference
    private lateinit var reminderPreference: SwitchPreference
    private lateinit var reminderReceiver: ReminderReceiver
    private lateinit var releaseTodayReceiver: ReleaseTodayReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init() {
        REMINDER = resources.getString(R.string.key_reminder)
        RELEASE = resources.getString(R.string.key_release)

        reminderPreference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
        releasePreference = findPreference<SwitchPreference>(RELEASE) as SwitchPreference

        reminderReceiver = ReminderReceiver()
        releaseTodayReceiver = ReleaseTodayReceiver()
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sh.getBoolean(REMINDER, false)
        releasePreference.isChecked = sh.getBoolean(RELEASE, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == REMINDER) {
            val value = sharedPreferences.getBoolean(REMINDER, false)
            if (value) {
                reminderReceiver.setRepeatingReminder(context!!)
                Log.d("Preference", "setReminder")
            } else {
                reminderReceiver.cancelRepeatingReminder(context!!)
                Log.d("Preference", "cancelReminder")
            }
            reminderPreference.isChecked = value
        }

        if (key == RELEASE) {
            val value = sharedPreferences.getBoolean(RELEASE, false)
            if (value) {
                releaseTodayReceiver.setReleaseTodayNotification(context!!)
                Log.d("Preference", "setRelease")
            } else {
                releaseTodayReceiver.cancelReleaseTodayNotification(context!!)
                Log.d("Preference", "cancelRelease")
            }
            releasePreference.isChecked = value
        }
    }
}