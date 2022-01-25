package com.als.l2.view.tread

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.als.l2.R

const val CHANNEL_ID = "ForegroundServiceChannel"

class ForegroundService : Service() {
    var messageId = 1000
    var factorial = 1L

    var running = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        running = true
        Thread(Runnable {
            while (running){
                try {
                    Thread.sleep(1000)
                } catch (e: Exception){
                    e.localizedMessage
                }
                factorial *= ++factorial
                val notification = makeNotification("Next factorial $factorial")
                val nc = NotificationChannel("ChanelMain", "Test",NotificationManager.IMPORTANCE_HIGH)
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(nc)
                notificationManager.notify(messageId, notification)
                startForeground(messageId++, makeNotification("Foreground Service"))
            }
        }).start()
        return START_NOT_STICKY
    }

    private fun makeNotification(message: String): Notification =

        NotificationCompat.Builder(this, "2")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Main service notification")
            .setContentText(message)
            .build()



    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        running = false
        super.onDestroy()
    }
}