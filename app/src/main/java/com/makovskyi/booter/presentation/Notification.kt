package com.makovskyi.booter.presentation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import kotlin.time.Duration.Companion.milliseconds

import android.os.Build

import android.content.Context
import android.content.pm.PackageManager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager

import android.media.AudioAttributes
import android.media.RingtoneManager

import android.annotation.SuppressLint
import androidx.annotation.RequiresApi

import androidx.core.content.ContextCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.makovskyi.booter.R
import com.makovskyi.booter.core.util.isAndroid8
import com.makovskyi.booter.core.util.isAndroid10
import com.makovskyi.booter.core.util.isAndroid13
import com.makovskyi.booter.platform.receiver.NotificationReceiver

object Notificator {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private const val PERMISSION = android.Manifest.permission.POST_NOTIFICATIONS

    private const val CHANNEL_ID = "boot_events"
    private const val CHANNEL_NAME = "Boot events"
    private const val NOTIFICATION_ID = 1001

    fun post(
        context: Context,
        content: NotificationContent
    ) {
        val manager = NotificationManagerCompat.from(context)
        createChannel(manager)
        publishNotification(
            message = content.message(),
            context = context,
            manager = manager
        )
    }

    private fun createChannel(manager: NotificationManagerCompat) {
        if (isAndroid8()) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Boot event notifications will be delivered here"
            channel.setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                AudioAttributes.Builder()
                    .also { attrs ->
                        attrs.setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        attrs.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        if (isAndroid10()) {
                            attrs.setAllowedCapturePolicy(AudioAttributes.ALLOW_CAPTURE_BY_ALL)
                        }
                    }.build()
            )
            channel.setShowBadge(true)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(150, 300, 150)
            channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            manager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission", "LaunchActivityFromNotification")
    private fun publishNotification(
        message: String,
        context: Context,
        manager: NotificationManagerCompat
    ) {
        if (!isPermissionGranted(context)) return

        val dismissIntent = NotificationReceiver.dismissIntent(context)
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .apply {
                if (!isAndroid8()) {
                    setDefaults(Notification.DEFAULT_ALL)
                    setPriority(Notification.PRIORITY_HIGH)
                }
            }
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentText(message)
            .setContentTitle("Boot Event")
            .setDeleteIntent(dismissIntent)
            .setContentIntent(dismissIntent) //but better to launch activity here
        manager.notify(
            NOTIFICATION_ID,
            notificationBuilder.build()
        )
    }

    private fun isPermissionGranted(context: Context): Boolean {
        if (!isAndroid13()) return true
        return ContextCompat.checkSelfPermission(context, PERMISSION) == PackageManager.PERMISSION_GRANTED
    }
}

sealed interface NotificationContent {

    @Suppress("ConvertObjectToDataObject")
    object BootEventNone : NotificationContent {

        override fun message(): String = "No boots detected"
    }

    class BootEventSingle(private val time: Long) : NotificationContent {

        override fun message(): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return buildString {
                append("The boot was detected = ")
                append(dateFormat.format(Date(time)))
            }
        }
    }

    class BootEventMultiple(private val delta: Long) : NotificationContent {

        override fun message(): String {
            return buildString {
                append("Last boots time delta(seconds) = ")
                append(delta.milliseconds.inWholeSeconds)
            }
        }
    }

    fun message(): String
}
