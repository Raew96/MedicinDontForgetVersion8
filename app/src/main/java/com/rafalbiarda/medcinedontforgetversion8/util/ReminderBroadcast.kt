package com.rafalbiarda.medcinedontforgetversion8.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.ui.activity.MainActivity

class ReminderBroadcast : BroadcastReceiver() {

    private val CHANNEL_1_ID = "CHANNEL1";
    private val CHANNEL_2_ID = "CHANNEL2"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val builder = NotificationCompat.Builder(context!!, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_keyboard_arrow_down_black_24dp)
            .setContentTitle("Reminder")
            .setContentText("Take Medicine")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val channel1 = NotificationChannel(CHANNEL_1_ID, "Channel1", NotificationManager.IMPORTANCE_HIGH)
        channel1.description = "This is channel 1"

        val i = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_ONE_SHOT)
        builder.setContentIntent(pendingIntent)

        val notificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE)  as NotificationManager
        notificationManager.createNotificationChannel(channel1)
        notificationManager.notify(200, builder.build())
    }
}