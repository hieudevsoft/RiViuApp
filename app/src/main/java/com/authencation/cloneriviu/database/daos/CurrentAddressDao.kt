package com.authencation.cloneriviu.database.daos

import androidx.room.*
import com.authencation.cloneriviu.database.entities.CurrentAddressEntity
import com.authencation.cloneriviu.support.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentAddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: CurrentAddressEntity)

    @Query("SELECT * FROM ${Constants.TABLE_OLD_CURRENT_LOCATION} ORDER BY id DESC")
    fun readLocations(): Flow<List<CurrentAddressEntity>>

    @Query("SELECT COUNT(*) from ${Constants.TABLE_OLD_CURRENT_LOCATION}")
    fun getCount(): Flow<Int>

    @Delete
    suspend fun deleteLocation(location: CurrentAddressEntity)

    @Query("delete from ${Constants.TABLE_OLD_CURRENT_LOCATION} where id = (SELECT id  FROM ${Constants.TABLE_OLD_CURRENT_LOCATION} LIMIT 1)")
    suspend fun deleteRowFirst()

    @Query("delete from ${Constants.TABLE_OLD_CURRENT_LOCATION} where name = :nameLocation")
    suspend fun deleteContentSameTableOldLocation(nameLocation:String)


}