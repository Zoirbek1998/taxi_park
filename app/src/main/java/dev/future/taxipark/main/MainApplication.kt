package dev.future.taxipark.main
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.future.taxipark.di.companent.DaggerAppComponent
import io.paperdb.Paper


class MainApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
     return DaggerAppComponent.builder().application(this).build()
    }
    override fun onCreate() {
        super.onCreate()

        instance = this
        Paper.init(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }



    }
    companion object {
        lateinit var instance: MainApplication private set
    }
}

