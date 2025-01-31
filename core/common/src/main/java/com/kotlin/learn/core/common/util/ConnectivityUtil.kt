package com.kotlin.learn.core.common.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_VPN
import android.net.NetworkCapabilities.TRANSPORT_WIFI

object ConnectivityUtil {

    fun isConnected(context: Context?): Boolean = context?.run {
        hasNetworkCapabilities(getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)
    } ?: false

    private fun hasNetworkCapabilities(connectivityManager: ConnectivityManager) =
        connectivityManager.activeNetwork?.run {
        connectivityManager.getNetworkCapabilities(this)
    }?.run {
        hasTransport(TRANSPORT_WIFI) ||
                hasTransport(TRANSPORT_CELLULAR) ||
                hasTransport(TRANSPORT_VPN)
    } ?: false

}