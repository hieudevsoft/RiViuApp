package com.authencation.cloneriviu.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.authencation.cloneriviu.database.entities.CurrentAddressEntity
import com.authencation.cloneriviu.networks.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentLocationViewModel(private val repository : Repository):ViewModel() {
    val readOldLocations:LiveData<List<CurrentAddressEntity>> = repository.local?.readLocations()!!.asLiveData()
    val getCount:LiveData<Int> = repository.local?.getCount()!!.asLiveData()

     fun insertLocation(location:CurrentAddressEntity){
        viewModelScope.launch(Dispatchers.IO) {
                repository.local?.insertLocation(location)
        }
    }
     fun deleteLocation(location:CurrentAddressEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local?.deleteLocation(location)
        }
    }
    fun deleteRowFirst(){
        viewModelScope.launch(Dispatchers.IO) {  repository.local?.deleteRowFirst()}
    }
    fun deleteContentSameTableOldLocation(nameLocation:String){
        viewModelScope.launch(Dispatchers.IO) {  repository.local?.deleteContentSameTableOldLocation(nameLocation)}
    }

}