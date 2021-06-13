package com.authencation.cloneriviu.viewmodels


import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.authencation.cloneriviu.database.entities.CurrentAddressEntity
import com.authencation.cloneriviu.model.BaseLogin
import com.authencation.cloneriviu.networks.Repository
import com.authencation.cloneriviu.networks.RepositoryLogin
import com.authencation.cloneriviu.networks.SignInOptionsFactory
import com.authencation.cloneriviu.support.LoginResultClient
import com.authencation.cloneriviu.support.LogoutResultClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginViewModel(val repository : RepositoryLogin):ViewModel() {
    val didLoginSuccessFully = MutableLiveData(false)

    fun getLoginSuccess():LiveData<Boolean>{
        didLoginSuccessFully.value = FirebaseAuth.getInstance().currentUser!=null
        return didLoginSuccessFully
    }
    fun createInstance(option: Int) {
        repository.createInstance(option)
    }
    fun login(activity: Activity,data: Intent?=null): LiveData<LoginResultClient<Boolean>?> {
        return repository.login(activity,data)
    }
    fun logout(activity: Activity): LiveData<LogoutResultClient<Boolean>?> {
        return repository.logout(activity)
    }
}