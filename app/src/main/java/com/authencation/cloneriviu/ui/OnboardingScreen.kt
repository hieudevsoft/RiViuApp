package com.authencation.cloneriviu.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.adapters.SliderOnboardingAdapter
import com.authencation.cloneriviu.databinding.ActivityOnboardingScreenBinding
import com.authencation.cloneriviu.model.BottomSheetFactory
import com.authencation.cloneriviu.networks.GoogleApi
import com.authencation.cloneriviu.networks.SignInWithFaceBook
import com.authencation.cloneriviu.support.*
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
    lateinit var googleApi: GoogleApi
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnboardingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Support.setFlagFullScreen(window)
        googleApi = GoogleApi.getInstance()
        googleApi.activity = this
        initSliderView()
        binding.btnLogin.setOnClickListener {
            googleApi.checkPreConditions()
            if(GoogleApi.check) openBottomSheetLogin()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode== Constants.GOOGLE_REQUEST_REQUEST) {
            true -> if (resultCode != RESULT_OK) Toast.makeText(
                this,
                "Please Install GPS",
                Toast.LENGTH_SHORT
            ).show()
            else {
                googleApi.checkPreConditions()
            }
            false -> {
                Toast.makeText(this, "An occurring while starting Application", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}