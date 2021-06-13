package com.authencation.cloneriviu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.authencation.cloneriviu.database.daos.CurrentAddressDao
import com.authencation.cloneriviu.database.entities.CurrentAddressEntity
import com.authencation.cloneriviu.database.type_converters.CurrentAddressTypeConverter
import com.authencation.cloneriviu.support.Constants

@Database(
    entities = [CurrentAddressEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(CurrentAddressTypeConverter::class)
abstract class CurrentLocationDatabases : RoomDatabase() {
    abstract fun getDao(): CurrentAddressDao

    companion object {
        @Volatile
        private var INSTANCE: CurrentLocationDatabases? = null
        fun getDatabase(context: Context): CurrentLocationDatabases {
            val database = INSTANCE
            if (database != null) return database
            else synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CurrentLocationDatabases::class.java,
                    Constants.DB_OLD_CURRENT_LOCATION
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}