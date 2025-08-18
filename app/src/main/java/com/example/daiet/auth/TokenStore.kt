package com.example.daiet.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth")

object TokenStore {
    private val KEY = stringPreferencesKey("access_token")

    suspend fun save(context: Context, token: String) {
        context.dataStore.edit { it[KEY] = token }
    }

    suspend fun get(context: Context): String? =
        context.dataStore.data.map { it[KEY] }.first()
}
