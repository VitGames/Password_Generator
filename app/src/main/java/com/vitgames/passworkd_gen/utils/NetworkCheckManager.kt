package com.vitgames.passworkd_gen.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


interface NetworkCheckManager {
    fun isNetworkEnabled(): Boolean
}

class NetworkCheckManagerImpl @Inject constructor(
    private val context: Context,
    private val logger: Logger
) : NetworkCheckManager {

    override fun isNetworkEnabled(): Boolean {
        var isNetworkEnabled = false
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.activeNetworkInfo != null) {
            isNetworkEnabled = connectivityManager.activeNetworkInfo!!.isConnected
        } else {
            logger.printError("ERROR: connectivityManager.activeNetworkInfo null")
        }
        return isNetworkEnabled
    }
}