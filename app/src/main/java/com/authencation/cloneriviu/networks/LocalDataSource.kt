package com.authencation.cloneriviu.networks

import com.authencation.cloneriviu.database.daos.CurrentAddressDao
import com.authencation.cloneriviu.database.entities.CurrentAddressEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val oldLocationDao:CurrentAddressDao) {

    suspend fun insertLocation(location: CurrentAddressEntity){
        oldLocationDao.insertLocation(location)
    }
    fun readLocations(): Flow<List<CurrentAddressEntity>>{
        return oldLocationDao.readLocations()
    }

    fun getCount(): Flow<Int>{
        return oldLocationDao.getCount()
    }

    suspend fun deleteLocation(location: CurrentAddressEntity){
        oldLocationDao.deleteLocation(location)
    }

    suspend fun deleteRowFirst(){
        oldLocationDao.deleteRowFirst()
    }
    suspend fun deleteContentSameTableOldLocation(nameLocation:String){
        oldLocationDao.deleteContentSameTableOldLocation(nameLocation)
    }

}