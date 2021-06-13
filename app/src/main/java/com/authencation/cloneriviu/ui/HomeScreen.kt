package com.authencation.cloneriviu.ui

import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.databinding.ActivityHomeScreenBinding
import com.authencation.cloneriviu.networks.NetworkConnecting
import com.authencation.cloneriviu.networks.RepositoryLogin
import com.authencation.cloneriviu.networks.SignInWithFaceBook
import com.authencation.cloneriviu.networks.SignInWithGoogle
import com.authencation.cloneriviu.receiver.NetworkReciever
import com.authencation.cloneriviu.support.*
import com.authencation.cloneriviu.viewmodels.LoginViewModel
import com.authencation.cloneriviu.viewmodels.factories.LoginViewModelsFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeScreen : AppCompatActivity(),NetworkReciever.OnNetworkChangeListener{
    private val TAG = "HomeScreen"
    lateinit var networkConnecting: NetworkConnecting
    private lateinit var _binding: ActivityHomeScreenBinding
    lateinit var loginViewModel: LoginViewModel
    private val binding get() = _binding
    lateinit var homeScreenListener:HomeScreenListener
    interface HomeScreenListener{
        fun onLoginGoogle(state: Boolean)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.navHostFragment)
        loginViewModel =
            ViewModelProvider(this, LoginViewModelsFactory(RepositoryLogin.getInstance())).get(
                LoginViewModel::class.java
            )
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
        try {
            when (loginViewModel.repository.loginInstance) {
                is SignInWithGoogle -> {
                    if (requestCode == Constants.LOGIN_GOOGLE && resultCode == RESULT_OK) {
                        loginViewModel.login(this, data).observe(this, {
                            Log.d(
                                TAG,
                                "controlEvent: ${loginViewModel.repository.loginInstance} ${SignInWithGoogle.getInstance()}"
                            )
                            when (it) {
                                is LoginResultClient.Success -> {
                                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT)
                                        .show()
                                    GlobalScope.launch(Dispatchers.IO) {
                                        val dataStoreLocal = DataStoreLocal(this@HomeScreen)
                                        dataStoreLocal.saveOptionLogin(2)
                                    }
                                    homeScreenListener.onLoginGoogle(true)
                                }
                                is LoginResultClient.Error -> {
                                    homeScreenListener.onLoginGoogle(false)
                                    Log.d(TAG, "show: ${it.message}")
                                    Toast.makeText(
                                        this@HomeScreen,
                                        "Login Failure",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> Log.d(TAG, "show: Error stranger")
                            }
                        })
                        Log.d(TAG, "onActivityResult: Login with Google")
                    }
                }
                is SignInWithFaceBook -> {
                    SignInWithFaceBook.getInstance().mCallBackManager.onActivityResult(
                        requestCode,
                        resultCode,
                        data
                    )
                    Log.d(TAG, "onActivityResult: Login with FaceBook")
                }
                else -> Log.d(TAG, "onActivityResult: Login with Others Option")
            }

        } catch (e: Exception) {
            Log.d(TAG, "onActivityResult: ${e.message}")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}