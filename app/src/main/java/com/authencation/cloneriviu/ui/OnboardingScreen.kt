package com.authencation.cloneriviu.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.adapters.SliderOnboardingAdapter
import com.authencation.cloneriviu.databinding.ActivityOnboardingScreenBinding
import com.authencation.cloneriviu.model.BottomSheetFactory
import com.authencation.cloneriviu.networks.GoogleApi
import com.authencation.cloneriviu.networks.RepositoryLogin
import com.authencation.cloneriviu.networks.SignInWithFaceBook
import com.authencation.cloneriviu.networks.SignInWithGoogle
import com.authencation.cloneriviu.support.*
import com.authencation.cloneriviu.viewmodels.LoginViewModel
import com.authencation.cloneriviu.viewmodels.factories.LoginViewModelsFactory
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.appevents.AppEventsLogger.activateApp
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OnboardingScreen : AppCompatActivity(),BottomSheetDialogLogin.BottomSheetListener {
    private val TAG = "onboarding_screen"
    private lateinit var _binding :ActivityOnboardingScreenBinding
    lateinit var loginViewModel:LoginViewModel
    lateinit var sliderOnboardingAdapter: SliderOnboardingAdapter
    lateinit var loginBox : BottomSheetDialogLogin
    lateinit var googleApi: GoogleApi
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnboardingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Support.setFlagFullScreen(window)
        googleApi = GoogleApi.getInstance()
        googleApi.activity = this
        loginViewModel =
            ViewModelProvider(this, LoginViewModelsFactory(RepositoryLogin.getInstance())).get(
                LoginViewModel::class.java
            )
        initSliderView()
        binding.btnLogin.setOnClickListener {
            googleApi.checkPreConditions()
            if(GoogleApi.check) openBottomSheetLogin()

        }
        binding.tvLater.setOnClickListener {
            moveHomeScreen()
        }
    }
    private fun moveHomeScreen() = Intent(this,HomeScreen::class.java).also {
        startActivity(it)
    }
    private fun openBottomSheetLogin() {
        loginBox = BottomSheetDialogLogin.newInstance()
        loginBox.lifeCycleOwner = this
        loginBox.bottomSheetListener = this
        loginBox.show(supportFragmentManager,"loginbox")

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(hasFocus) Support.setFlagFullScreen(window)
    }
    fun initSliderView(){
        sliderOnboardingAdapter = SliderOnboardingAdapter()
        sliderOnboardingAdapter.setData(Support.createListSlider())
        binding.sliderView.setSliderAdapter(sliderOnboardingAdapter,true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            when(requestCode==Constants.GOOGLE_REQUEST_REQUEST){
                true->if(resultCode!= RESULT_OK) Toast.makeText(this, "Please Install GPS", Toast.LENGTH_SHORT).show()
                else{
                    val googleApi = GoogleApi.getInstance()
                    googleApi.activity = this
                    googleApi.checkPreConditions()
                }
                false->when (loginViewModel.repository.loginInstance) {
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
                                            val dataStoreLocal = DataStoreLocal(this@OnboardingScreen)
                                            dataStoreLocal.saveOptionLogin(2)
                                        }
                                        loginBox.dismiss()
                                        moveHomeScreen()
                                    }
                                    is LoginResultClient.Error -> {
                                        Log.d(TAG, "show: ${it.message}")
                                        Toast.makeText(
                                            this@OnboardingScreen,
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
            }

        } catch (e: Exception) {
            Log.d(TAG, "onActivityResult: ${e.message}")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onLoginFacebook(state: Boolean) {
        if(state) moveHomeScreen() else{
            WidgetOwner.makeSnackbarView(binding.btnLogin,"Login FaceBook Failure",false)
            loginBox.dismiss()
        }
    }
}