package com.authencation.cloneriviu.database.type_converters

import androidx.room.TypeConverter
import com.authencation.cloneriviu.database.entities.CurrentAddressEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentAddressTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun oldLocationToString(foodRecipe: CurrentAddressEntity): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToOldLocation(data: String): CurrentAddressEntity {
        val listType = object : TypeToken<CurrentAddressEntity>() {}.type
        return gson.fromJson(data, listType)
    }

}