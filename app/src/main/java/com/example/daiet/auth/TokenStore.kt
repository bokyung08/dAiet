package com.example.daiet.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// 파일 최상단(클래스 밖)에 있어야 합니다.
private val Context.dataStore by preferencesDataStore(name = "auth")

object TokenStore {
    private val KEY = stringPreferencesKey("access_token")

    /** 토큰 저장 */
    suspend fun save(context: Context, token: String) {
        context.dataStore.edit { it[KEY] = token }
    }

    /** 토큰 1회 읽기 (없으면 null) */
    suspend fun get(context: Context): String? =
        context.dataStore.data.map { it[KEY] }.first()

    /** 토큰 삭제 */
    suspend fun clear(context: Context) {
        context.dataStore.edit { it.remove(KEY) }
    }
}
