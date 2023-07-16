package com.sn30.smartmemo

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sn30.smartmemo.services.MemoRepository
import com.sn30.smartmemo.services.SettingsRepository

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val Context.memoDataStore: DataStore<MemoList> by dataStore(
    fileName = "memoList.db",
    serializer = MemoListSerializer
)

class MemoApp : Application() {

    val settingsRepository: SettingsRepository
        get() = SettingsRepository(settingsDataStore)

    val memoRepository: MemoRepository
        get() = MemoRepository(memoDataStore)
}