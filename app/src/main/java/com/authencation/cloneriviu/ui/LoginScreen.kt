package com.authencation.cloneriviu.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.R.navigation.nav_host
import com.authencation.cloneriviu.databinding.ActivityLoginScreenBinding
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.ui.registers.SendOtpScreen

class LoginScreen : AppCompatActivity() {
    private lateinit var _binding: ActivityLoginScreenBinding
    private val USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"
    private val PHONE_NUMBER_PATTERN = "^\\d{10}$"
    private var isPassOk = false
    private var isUsernamePhoneOk = false
    private val binding get() = _binding!!
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Support.setFlagFullScreen(window)
        binding.showError = false
        binding.backGroundButton = false
        binding.edtUserPhone.addTextChangedListener {
            val text = it.toString()
            if (text.isNotEmpty()) {
                if (!isValidPhone(text) && !isValidUser(text)) {
                    binding.showError = true
                    binding.errorText = "User name hoặc số điện thoại không đúng"
                    isUsernamePhoneOk = true
                    binding.edtUserPhone.setBackgroundResource(R.drawable.custom_login_edt_error)
                } else {
                    binding.edtUserPhone.setBackgroundResource(R.drawable.custom_login_edt_event)
                    binding.showError = false
                    binding.errorText = ""
                    binding.backGroundButton = isPassOk
                }
            }
        }
        binding.edtPassword.addTextChangedListener {
            val text = it.toString()
            if (text.isNotEmpty()) {
                if (!isValidPass(text)) {
                    binding.showError = true
                    binding.errorText = "Mật khẩu phải trên 6 ký tự"
                    isPassOk = true
                    binding.edtPassword.setBackgroundResource(R.drawable.custom_login_edt_error)
                } else {
                    binding.edtPassword.setBackgroundResource(R.drawable.custom_login_edt_event)
                    binding.showError = false
                    binding.errorText = ""
                    binding.backGroundButton = isUsernamePhoneOk
                }
            }
        }
        binding.imgBack.setOnClickListener { finish() }
        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this,SendOtpScreen::class.java))
            overridePendingTransition(R.anim.nav_default_enter_anim,R.anim.nav_default_exit_anim)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasWindowFocus()) Support.setFlagFullScreen(window)
    }


    private fun isValidUser(username: String): Boolean {
        return username.matches(USERNAME_PATTERN.toRegex())
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.matches(PHONE_NUMBER_PATTERN.toRegex())
    }

    private fun isValidPass(password: String): Boolean = password.length >= 6
}