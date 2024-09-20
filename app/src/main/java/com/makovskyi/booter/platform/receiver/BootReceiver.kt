package com.makovskyi.booter.platform.receiver

import android.content.Intent
import android.content.Context
import android.content.BroadcastReceiver

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
        }
    }
}
