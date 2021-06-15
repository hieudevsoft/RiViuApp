package com.authencation.cloneriviu.networks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.authencation.cloneriviu.model.BaseLogin
import com.authencation.cloneriviu.support.DataStoreLocal
import com.authencation.cloneriviu.support.LoginResultClient
import com.authencation.cloneriviu.support.LogoutResultClient
import com.authencation.cloneriviu.viewmodels.LoginViewModel
import com.authencation.cloneriviu.viewmodels.factories.LoginViewModelsFactory
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class SignInWithFaceBook private constructor() : BaseLogin {
    lateinit var mCallBackManager: CallbackManager
    lateinit var loginResultClient: MutableLiveData<LoginResultClient<Boolean>?>
    private object Holder {
        val instance: SignInWithFaceBook = SignInWithFaceBook()
    }

    companion object {
        private val TAG = "SignInWithFaceBook"
        fun getInstance(): SignInWithFaceBook {
            Log.d(TAG, "getInstance Holder: ${Holder.instance}")
            return Holder.instance
        }
    }

    override fun login(activity: Activity, data: Intent?): LiveData<LoginResultClient<Boolean>?> {
        loginResultClient = MutableLiveData<LoginResultClient<Boolean>?>()
        FacebookSdk.sdkInitialize(activity)
        mCallBackManager = CallbackManager.Factory.create()
             LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_VIEW_ONLY
             LoginManager.getInstance().registerCallback(mCallBackManager, object :
                 FacebookCallback<LoginResult> {
                 override fun onSuccess(result: LoginResult?) {
                     Log.d(TAG, "onSuccess: SuccessFaceBook")
                     handlerFacebookToken(result?.accessToken)
                 }

                 override fun onCancel() {
                     Log.d(TAG, "onCancel: CancelFacebook")
                     loginResultClient.value = LoginResultClient.Error("CANCEL REQUEST", false)
                 }

                 override fun onError(error: FacebookException?) {
                     if (error != null) {
                         Log.d(TAG, "onError: ErrorFacebook")
                         loginResultClient.value = LoginResultClient.Error("Error: ${error.message}", false)
                     }
                 }
             })

         LoginManager.getInstance()
             .logInWithReadPermissions(
                 activity,
                 listOf("email", "public_profile")
             )
        return loginResultClient
    }

    private fun handlerFacebookToken(accessToken: AccessToken?) {
        Log.d(TAG, "handlerFacebookToken: HandelToken $accessToken")
        val credential = FacebookAuthProvider.getCredential(accessToken?.token!!)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                loginResultClient.value = LoginResultClient.Success(true)
            }else loginResultClient.value = LoginResultClient.Error("Error login firebase",false)
        }
    }

    override fun logout(activity: Activity): LiveData<LogoutResultClient<Boolean>?> {
        val logout = MutableLiveData<LogoutResultClient<Boolean>?>()
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Question ?")
        dialog.setMessage("Do you want Sign Out?")
        dialog.setPositiveButton(
            "Yes"
        ) { _, _ ->
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().let {
                LoginManager.getInstance().logOut()
            }
            logout.value = LogoutResultClient.Success(true)
        }
        dialog.setNegativeButton("No") { _, _ ->
            logout.value = LogoutResultClient.Error("Cancel Logout", false)
        }
        dialog.show()
        return logout
    }
}


