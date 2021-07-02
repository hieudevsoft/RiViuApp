package com.authencation.cloneriviu.ui.registers

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.support.WidgetOwner
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class SendOtpScreen : AppCompatActivity() {
    private val TAG = "SendOtpScreen" 
    lateinit var buttonSendOTP:CircularProgressButton
    lateinit var edtPhone:EditText
    lateinit var imageBack:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registration)
        Support.setFlagFullScreen(window)
        edtPhone = findViewById(R.id.edtPhone)
        imageBack = findViewById(R.id.imgBack)
        buttonSendOTP = findViewById(R.id.btnSendOTP)

        buttonSendOTP.setOnClickListener {
            buttonSendOTP.startAnimation()
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(String.format("+84%s",edtPhone.text.toString().trim()))
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted: onCompleted: $p0")
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                    
                        if (p0 is FirebaseAuthInvalidCredentialsException) {
                            Log.d(TAG, "onVerificationFailed: InvalidRequest")
                        } else if (p0 is FirebaseTooManyRequestsException) {
                            Log.d(TAG, "onVerificationFailed: ToomanyRequest")
                        } else{
                            WidgetOwner.makeSnackbarView(buttonSendOTP,"No Internet Connection",false)
                            Log.d(TAG, "onVerificationFailed: No Connect internet")
                        }
                        buttonSendOTP.revertAnimation()
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        Log.d(TAG, "onCodeSent: token: $p1 , verificationId: $p0")
                        buttonSendOTP.revertAnimation()
                        startActivity(Intent(this@SendOtpScreen,VerifyOtpScreen::class.java).also {
                            it.putExtra("phoneNumber",edtPhone.text.toString())
                        })
                        overridePendingTransition(R.anim.nav_default_enter_anim,R.anim.nav_default_exit_anim)
                        super.onCodeSent(p0, p1)
                    }

                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        imageBack.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        buttonSendOTP.dispose()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(hasFocus) Support.setFlagFullScreen(window)
    }
}