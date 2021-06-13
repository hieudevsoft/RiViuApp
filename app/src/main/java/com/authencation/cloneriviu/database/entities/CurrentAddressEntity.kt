package com.authencation.cloneriviu.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.authencation.cloneriviu.support.Constants.TABLE_OLD_CURRENT_LOCATION


@Entity(tableName = TABLE_OLD_CURRENT_LOCATION)
class CurrentAddressEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var name: String
)