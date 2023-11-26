package com.fmq.ticketmonitoringsystem.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isNetworkAvailable(): Boolean {
    with(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
        getNetworkCapabilities(activeNetwork)?.let {
            return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && it.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
        }
    }
    return false
}