package com.authencation.cloneriviu.viewmodels.factories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.authencation.cloneriviu.networks.Repository
import com.authencation.cloneriviu.networks.RepositoryLogin
import com.authencation.cloneriviu.viewmodels.CurrentLocationViewModel
import com.authencation.cloneriviu.viewmodels.LoginViewModel
import java.lang.IllegalArgumentException

class LoginViewModelsFactory(private val repository: RepositoryLogin): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("LoginViewModel not found")
    }
}