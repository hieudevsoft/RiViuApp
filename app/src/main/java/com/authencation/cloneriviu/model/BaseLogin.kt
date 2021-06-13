package com.authencation.cloneriviu.model

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import com.authencation.cloneriviu.support.LoginResultClient
import com.authencation.cloneriviu.support.LogoutResultClient
import com.authencation.cloneriviu.viewmodels.LoginViewModel


interface BaseLogin {
    fun login(activity: Activity,data: Intent?=null):LiveData<LoginResultClient<Boolean>?>
    fun logout(activity: Activity):LiveData<LogoutResultClient<Boolean>?>

}