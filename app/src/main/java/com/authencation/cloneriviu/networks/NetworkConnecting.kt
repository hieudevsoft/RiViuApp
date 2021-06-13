package com.authencation.cloneriviu.networks

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.authencation.cloneriviu.receiver.NetworkReciever


class NetworkConnecting private constructor() {
    var context:Context?=null
    object Holder{
        val networkConnecting = NetworkConnecting()
    }
    companion object{
        @JvmStatic val reciever = NetworkReciever()
        val instance : NetworkConnecting by lazy { Holder.networkConnecting }
    }

    fun attachStateNetworking(onNetworkChangeListener: NetworkReciever.OnNetworkChangeListener){
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context?.registerReceiver(reciever,intentFilter)
        reciever.onNetworkChangeListener = onNetworkChangeListener
    }
    fun dettachStateNetworking(){
        context?.unregisterReceiver(reciever)
    }
}