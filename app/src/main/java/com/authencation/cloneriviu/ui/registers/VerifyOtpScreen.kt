package com.authencation.cloneriviu.ui.registers

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.authencation.cloneriviu.R
import com.authencation.cloneriviu.support.Support
import com.authencation.cloneriviu.support.WidgetOwner
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.mukesh.OtpView
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar
import java.util.concurrent.TimeUnit

class VerifyOtpScreen:AppCompatActivity() {
    private val TAG = "VerifyOtpScreen"
    lateinit var tvPhone: TextView
    lateinit var tvTime: TextView
    lateinit var btnResend: CircularProgressButton
    lateinit var countDownTimer: CountDownTimer
    lateinit var pinView: OtpView
    lateinit var progressBar:DilatingDotsProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registration2)
        Support.setFlagFullScreen(window)
        mapping()
        initTextPhone()
        initPinView()
        initResend()
        timeCountDown()
    }
    private fun initResend(){
        btnResend.setOnClickListener {
            timeCountDown()
            progressBar.hideNow()
            btnResend.startAnimation()
            FirebaseAuth.getInstance().signOut()
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(String.format("+84%s",tvPhone.text.toString().trim()))
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
                            WidgetOwner.makeSnackbarView(btnResend,"No Internet Connection",false)
                            Log.d(TAG, "onVerificationFailed: No Connect internet")
                        }
                        btnResend.revertAnimation()
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        Log.d(TAG, "onCodeSent: token: $p1 , verificationId: $p0")
                        btnResend.revertAnimation()
                        WidgetOwner.makeSnackbarView(btnResend,"OTP sent ~",true)
                        super.onCodeSent(p0, p1)
                    }

                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun mapping(){
        tvPhone = findViewById(R.id.text_sdt3)
        pinView = findViewById(R.id.otp_view)
        tvTime  = findViewById(R.id.tvTime)
        progressBar = findViewById(R.id.progressBar)
        btnResend = findViewById(R.id.btnResend)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if(hasWindowFocus()) Support.setFlagFullScreen(window)
    }
    private fun initPinView(){
        pinView.setAnimationEnable(true)
        pinView.setOtpCompletionListener {
            progressBar.showNow()
            if(intent.getStringExtra("verificationId")!=null){
                Toast.makeText(this, "vao day r", Toast.LENGTH_SHORT).show()
                val verificationId = intent.getStringExtra("verificationId")
                val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId!!,it)
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            btnResend.revertAnimation()
                            progressBar.hideNow()
                            WidgetOwner.makeSnackbarView(tvTime,"Register via OTP Successfully",true)
                        }else{
                            btnResend.revertAnimation()
                            progressBar.hideNow()
                            WidgetOwner.makeSnackbarView(tvTime,"Register via OTP failure",false)
                            pinView.setText("")
                        }
                    }
            }

        }
    }
    private fun initTextPhone(){
        try {
            val textPhone = intent.getStringExtra("phoneNumber")
            tvPhone.text = textPhone
        }catch (e:Exception){

        }
    }

    private fun timeCountDown(){
        countDownTimer.cancel()
        var time = 60
        tvTime.text = time.toString()
        countDownTimer = object : CountDownTimer(60000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                time--
                tvTime.text = time.toString()+"s"

            }

            override fun onFinish() {
                WidgetOwner.makeSnackbarView(tvTime,"OTP is expired",false)
                progressBar.hideNow()
                btnResend.revertAnimation()
            }

        }
        countDownTimer.start()
    }

    override fun onDestroy() {
        btnResend.dispose()
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}