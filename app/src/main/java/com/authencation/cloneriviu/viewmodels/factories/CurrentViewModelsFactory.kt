package com.authencation.cloneriviu.viewmodels.factories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.authencation.cloneriviu.networks.Repository
import com.authencation.cloneriviu.viewmodels.CurrentLocationViewModel
import java.lang.IllegalArgumentException

class CurrentViewModelsFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentLocationViewModel::class.java)){
            return CurrentLocationViewModel(repository) as T
        }
        throw IllegalArgumentException("CurrentLocationViewModel not found")
    }
}