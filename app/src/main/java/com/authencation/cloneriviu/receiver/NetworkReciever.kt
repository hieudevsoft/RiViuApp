package com.authencation.cloneriviu.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

class NetworkReciever : BroadcastReceiver() {
    private val TAG = "NetworkReciever"
    lateinit var onNetworkChangeListener: OnNetworkChangeListener

    interface OnNetworkChangeListener {
        fun onNetworkChange(state: Boolean)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            Log.d(TAG, "onReceive: Has intent send from action ${intent.action}")
            onNetworkChangeListener.onNetworkChange(hasInternetConnection(context))
        }
    }

    companion object {
        fun hasInternetConnection(context: Context?): Boolean {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

        }
    }
}