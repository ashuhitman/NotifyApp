package com.example.flashnetapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.flashnetapp.Constants.NOTIFICATION_CHANNEL_ID
import com.example.flashnetapp.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.flashnetapp.Constants.NOTIFICATION_ID

class PaymentService:Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action.toString() == ("STOP_SERVICE")) {
            Log.d("click",intent?.action.toString())
            stopForeground(true);
            stopSelf()
            return  START_NOT_STICKY
        }
        startForegroundService()
        return  START_STICKY
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }
    private fun startForegroundService(){
        Log.d("click","starting foreground service...")
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        val notificationIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,notificationIntent,0)
        val broadcastIntent = Intent(this, NotificationReceiver::class.java)

        val actionIntent =
            PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
            .setContentTitle("Payment is pending")
            .setContentText("wish to open the app?")
            .setColor(Color.BLUE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(R.drawable.ic_launcher_background,"Yes",pendingIntent)
            .addAction(R.drawable.ic_launcher_background,"",null)
            .addAction(R.drawable.ic_launcher_background,"No",actionIntent)
            .build()
        startForeground(NOTIFICATION_ID,notificationBuilder)

    }

}