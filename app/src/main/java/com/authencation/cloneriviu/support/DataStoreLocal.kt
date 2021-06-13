package com.authencation.cloneriviu.support

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class DataStoreLocal(context: Context) {
    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name = Constants.PREFERENCES_NAME)

    private object PreferenceKeys {
        val selectedLocation = preferencesKey<String>(Constants.PREFERENCES_NAME_LOCATION)
        val optionLogin = preferencesKey<Int>(Constants.PREFERENCES_LOGIN_OPTION)
    }

    suspend fun saveLocationData(name: String) {
        dataStore.edit { pref ->
            pref[PreferenceKeys.selectedLocation] = name
        }
    }
    suspend fun saveOptionLogin(option: Int) {
        dataStore.edit { pref ->
            pref[PreferenceKeys.optionLogin] = option
        }
    }
    val readLocationData : Flow<String> = dataStore.data
        .catch {
                exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            return@map it[PreferenceKeys.selectedLocation]?:"Hà Nội"
        }
    val readOptionLogin : Flow<Int> = dataStore.data
        .catch {
                exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            return@map it[PreferenceKeys.optionLogin]?:0
        }
}