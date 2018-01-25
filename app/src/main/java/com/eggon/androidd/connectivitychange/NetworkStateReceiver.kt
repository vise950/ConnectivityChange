package com.eggon.androidd.connectivitychange

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import co.eggon.eggoid.extension.error


class NetworkStateReceiver : BroadcastReceiver() {

    private var network: NetworkInfo? = null
    private var networkType = ConnectivityManager.TYPE_DUMMY

    companion object {
        var wifiConnection: (() -> Unit)? = null
        var mobileConnection: (() -> Unit)? = null
        var noConnection: (() -> Unit)? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (isConnected(context)) {
                "connected".error()
                when (network?.type) {
                    ConnectivityManager.TYPE_WIFI -> {
                        "type wifi".error()
                        wifiConnection?.invoke()
                    }
                    ConnectivityManager.TYPE_MOBILE -> {
                        "type mobile".error()
                        mobileConnection?.invoke()
                    }
                    else -> {
                        "other type connection".error()
                    }
                }
            } else {
                "no connected".error()
                noConnection?.invoke()
            }
        }
    }

    private fun isConnected(context: Context?): Boolean {
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.also {
            network = it.activeNetworkInfo
        }
        return network?.isConnected ?: false
    }
}