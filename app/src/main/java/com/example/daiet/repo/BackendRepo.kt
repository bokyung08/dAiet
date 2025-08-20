package com.example.daiet.repo

import android.content.Context
import com.example.daiet.auth.TokenStore
import com.example.daiet.net.ApiBuilder
import com.example.daiet.net.LoginRequest
import com.example.daiet.net.MealRequest
import com.example.daiet.net.SignupRequest
import com.google.gson.Gson

class BackendRepo(private val context: Context) {
    private val api = ApiBuilder.create(context)

    suspend fun signup(email: String, password: String, name: String?, gender: String?) {
        api.signup(SignupRequest(email, password, name, gender))
    }

    suspend fun login(email: String, password: String) {
        val res = api.login(LoginRequest(email, password))
        TokenStore.save(context, res.access_token)
    }

    suspend fun me() = api.me()

    suspend fun addMeal(items: List<String>, calories: Int?, mealTimeIso: String?) =
        api.addMeal(
            MealRequest(
                items_json = Gson().toJson(items),
                calories   = calories,
                meal_time  = mealTimeIso
            )
        )

    suspend fun recentMeals(limit: Int = 5) = api.recentMeals(limit)

    /** 값이 없으면 필드를 빼서 422 방지 */
    suspend fun addChronic(name: String, details: String?, diagnosedAt: String?) {
        val body = mutableMapOf<String, Any>("name" to name)
        details?.trim()?.takeIf { it.isNotEmpty() }?.let { body["details"] = it }
        diagnosedAt?.trim()?.takeIf { it.isNotEmpty() }?.let { body["diagnosed_at"] = it } // YYYY-MM-DD만 허용
        api.addChronic(body)
    }

    suspend fun listChronic() = api.listChronic()

    suspend fun logout() = TokenStore.clear(context)

    /** UI에서 고른 질환 라벨(한글) → 서버 코드 매핑 */
    fun diseaseLabelToCode(label: String?): String? = when (label) {
        "체중감량" -> "weight_loss"
        "고혈압"   -> "hypertension"
        "당뇨"     -> "diabetes"
        "고지혈증" -> "hyperlipidemia"
        else       -> null // 선택 안 함
    }
}
