package com.authencation.cloneriviu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.databinding.ActivityHomeScreenBinding
import com.authencation.cloneriviu.networks.NetworkConnecting
import com.authencation.cloneriviu.networks.SignInWithFaceBook
import com.authencation.cloneriviu.receiver.NetworkReciever
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.support.WidgetOwner


class HomeScreen : AppCompatActivity(),NetworkReciever.OnNetworkChangeListener{
    lateinit var networkConnecting: NetworkConnecting
    private lateinit var _binding :ActivityHomeScreenBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.navHostFragment)
        binding.navigationBottom.setupWithNavController(navController)
        networkConnecting = NetworkConnecting.instance
        networkConnecting.context = this
    }

    override fun onResume() {
        Support.setFlagFullScreen(window)
        networkConnecting?.let {
            networkConnecting.attachStateNetworking(this)
        }
        super.onResume()
    }
    override fun onStop() {
        Toast.makeText(this, "onStop HomeScreen", Toast.LENGTH_SHORT).show()
        networkConnecting.dettachStateNetworking()
        super.onStop()
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(hasFocus) Support.setFlagFullScreen(window)
    }

    override fun onNetworkChange(state: Boolean) {
    WidgetOwner.makeSnackbar(binding.navigationBottom,state)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        SignInWithFaceBook.getInstance().mCallBackManager.onActivityResult(requestCode,resultCode,data)
        Log.d("SignInWithFaceBook", "onActivityResult: Result")
        super.onActivityResult(requestCode, resultCode, data)
    }
}