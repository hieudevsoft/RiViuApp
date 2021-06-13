package com.authencation.cloneriviu.networks

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import com.authencation.cloneriviu.model.BaseLogin
import com.authencation.cloneriviu.support.LoginResultClient
import com.authencation.cloneriviu.support.LogoutResultClient
import com.authencation.cloneriviu.viewmodels.LoginViewModel

class  RepositoryLogin private constructor() {
    lateinit var loginInstance: BaseLogin
    object Holder{
        val instance = RepositoryLogin()
    }
    companion object{
        fun getInstance() = Holder.instance
    }
    fun createInstance(option: Int) {
        SignInOptionsFactory.createInstance(option)
        loginInstance = SignInOptionsFactory.loginInstance
    }
    fun login(activity: Activity,data:Intent?=null): LiveData<LoginResultClient<Boolean>?> {
        return loginInstance.login(activity,data)
    }
    fun logout(activity: Activity): LiveData<LogoutResultClient<Boolean>?> = loginInstance.logout(activity)
}