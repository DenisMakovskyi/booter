package com.makovskyi.booter.platform.receiver

import android.content.Intent
import android.content.Context
import android.content.BroadcastReceiver

import android.app.PendingIntent

class NotificationReceiver : BroadcastReceiver() {

    companion object {

        const val ACTION_DISMISSED = "com.makovskyi.booter.action.NOTIFICATION_DISMISSED"

        fun dismissIntent(context: Context): PendingIntent {
            val intent = Intent(context, NotificationReceiver::class.java)
                .setAction(ACTION_DISMISSED)
            return PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_DISMISSED) {
        }
    }
}
