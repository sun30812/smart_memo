package com.sn30.smartmemo.services

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    val DELETE_CONFIRM = booleanPreferencesKey("delete_confirm")
    val deleteConfirmFlow: Flow<Boolean> = dataStore.data.map { preference ->
        preference[DELETE_CONFIRM] ?: false
    }

    suspend fun updateDeleteConfirmSetting(newValue: Boolean) {
        dataStore.edit {
            it[DELETE_CONFIRM] = newValue
        }
    }
}