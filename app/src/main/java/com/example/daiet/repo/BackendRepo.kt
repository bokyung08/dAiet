package com.example.daiet.repo

import android.content.Context
import com.example.daiet.auth.TokenStore
import com.example.daiet.net.ApiBuilder
import com.example.daiet.net.LoginRequest
import com.example.daiet.net.MealRequest
import com.example.daiet.net.SignupRequest

class BackendRepo(private val context: Context) {
    private val api = ApiBuilder.create(context)

    suspend fun signup(email: String, password: String, name: String?, gender: String?) {
        api.signup(SignupRequest(email, password, name, gender))
    }

    suspend fun login(email: String, password: String) {
        val t = api.login(LoginRequest(email, password))
        TokenStore.save(context, t.access_token)
    }

    suspend fun me() = api.me()

    suspend fun addMeal(items: List<String>, calories: Int?) =
        api.addMeal(MealRequest(items, calories))

    suspend fun recentMeals(limit: Int = 5) =
        api.recentMeals(limit)

    suspend fun addChronic(name: String, details: Nothing?, diagnosedAt: Nothing?) {

    }

}
