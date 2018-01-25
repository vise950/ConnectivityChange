package com.eggon.androidd.connectivitychange

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager


class Init : Application() {

    private val connectivityReceiver by lazy { NetworkStateReceiver() }
    private val connectivityIntent by lazy { IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION) }

    override fun onCreate() {
        super.onCreate()
        applicationContext.registerReceiver(connectivityReceiver, connectivityIntent)
//        JobManager.create(this).addJobCreator(JobCreator())
    }
}