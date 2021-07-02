package com.authencation.cloneriviu.ui.registers

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.authencation.cloneriviu.R

class VerifyOtpScreen:AppCompatActivity() {
    lateinit var tvPhone: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registration2)
        tvPhone = findViewById(R.id.text_sdt3)
        val textPhone = intent.getStringExtra("phoneNumber")
        tvPhone.text = textPhone
    }
}