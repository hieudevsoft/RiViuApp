package com.authencation.cloneriviu.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.adapters.SliderOnboardingAdapter
import com.authencation.cloneriviu.databinding.ActivityOnboardingScreenBinding
import com.authencation.cloneriviu.model.BottomSheetFactory
import com.authencation.cloneriviu.networks.SignInWithFaceBook
import com.authencation.cloneriviu.support.BottomSheetDialogLogin
import com.authencation.cloneriviu.support.LoginResultClient
import com.authencation.cloneriviu.support.Support
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.appevents.AppEventsLogger.activateApp
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class onboarding_screen : AppCompatActivity() {
    lateinit var _binding :ActivityOnboardingScreenBinding
    lateinit var sliderOnboardingAdapter: SliderOnboardingAdapter
    lateinit var loginBox : BottomSheetDialogLogin
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnboardingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Support.setFlagFullScreen(window)
        initSliderView()
        binding.btnLogin.setOnClickListener {
            openBottomSheetLogin()
        }
        binding.tvLater.setOnClickListener {
            Intent(this,HomeScreen::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun openBottomSheetLogin() {
        loginBox = BottomSheetDialogLogin.newInstance()
        loginBox.lifeCycleOwner = this
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


}