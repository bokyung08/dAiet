package com.example.daiet.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenStore(private val context: Context) {
    companion object {
        private val KEY_TOKEN = stringPreferencesKey("auth_token")
    }

    val tokenFlow = context.dataStore.data.map { it[KEY_TOKEN] }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[KEY_TOKEN] = token }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    // Interceptor에서 한 번만 읽어올 때 사용
    suspend fun getTokenOnce(): String? = tokenFlow.firstOrNull()
}
