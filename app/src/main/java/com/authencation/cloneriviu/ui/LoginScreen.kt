package com.authencation.cloneriviu.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.support.Support

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        Support.setFlagFullScreen(window)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(hasWindowFocus()) Support.setFlagFullScreen(window)
    }
}