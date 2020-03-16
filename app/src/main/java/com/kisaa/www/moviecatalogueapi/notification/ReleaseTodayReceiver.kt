package com.kisaa.www.moviecatalogueapi.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.kisaa.www.moviecatalogueapi.R
import com.kisaa.www.moviecatalogueapi.model.MovieResponse
import com.kisaa.www.moviecatalogueapi.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ReleaseTodayReceiver : BroadcastReceiver() {

    companion object {
        private const val ID_RELEASE = 102
    }

    override fun onReceive(context: Context, intent: Intent) {
        getReleasedMovie(context)
        Log.d("Release Receiver", "onReceive")
    }

    fun setReleaseTodayNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseTodayReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelReleaseTodayNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseTodayReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    private fun showReleaseTodayNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    ) {
        val CHANNEL_ID = "Channel_2"
        val CHANNEL_NAME = "Release Channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    private fun getReleasedMovie(context: Context) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.format(Date())

        NetworkConfig().api().getReleaseMovie(date, date).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val items = response.body()
                for (movies in items!!.results!!.iterator()) {
                    val title = movies.title!!
                    val message =
                        movies.title + " " + context.resources.getString(R.string.release_today)
                    val notifId = movies.id!!.toInt()
                    showReleaseTodayNotification(context, title, message, notifId)
                    Log.d("Release Reciever", title)
                }
            }
        })


    }
}
