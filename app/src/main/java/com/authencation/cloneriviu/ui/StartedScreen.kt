package com.authencation.cloneriviu.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.support.Support

class StartedScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.started_layout)
        Support.setFlagFullScreen(window)
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this,onboarding_screen::class.java).also {
                startActivity(it)
            }
            overridePendingTransition(R.anim.anim_from_right, R.anim.anim_from_left)
        }, 1000L)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) Support.setFlagFullScreen(window)
    }
}