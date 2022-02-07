package com.example.flashnetapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("click","received")
        val intent1 = Intent(p0?.applicationContext,PaymentService::class.java)
        intent1.action = "STOP_SERVICE"
        p0?.startService(intent1)
    }
}