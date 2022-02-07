package com.als.l2.experiment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.als.l2.R

private const val channelName = "NOTIF_CHANNEL"

private const val channelId = "NOTIF_CHANNEL_ID"

class TestForeground : Service() {

    override fun onCreate() {
        val title = "Запущена служба"
        val msg = "Foreground"
        Log.i("Test", "Service: onCreate")
        val notification: Notification = if (Build.VERSION.SDK_INT < 16) {
            val builder: Notification.Builder = Notification.Builder(this)
                // .setSmallIcon(R.drawable.fox)
                .setContentTitle(title)
                .setContentText(msg)
            builder.notification
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val builder: Notification.Builder = Notification.Builder(this)
                // .setSmallIcon(R.drawable.fox)
                .setContentTitle(title)
                .setContentText(msg)
            builder.build()
        } else {
            val builder: Notification.Builder = Notification.Builder(
                this,
                createNotifChanel(channelId, channelName)
            )
                // .setSmallIcon(R.drawable.fox)
                .setContentTitle(title)
                .setContentText(msg)
            builder.build()
        }
        startForeground(777, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifChanel(chanelId: String, chanelName: String): String? {
        val chan = NotificationChannel(chanelId, chanelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = R.color.colorPrimary
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return chanelId
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            for (i in 0..10) {
                Thread.sleep(1000)
            }
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }
}
