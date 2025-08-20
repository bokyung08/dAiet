package com.example.daiet.net

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String?,
    val gender: String?
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class TokenResponse(
    @SerializedName("access_token") val access_token: String
)

data class MeResponse(
    val id: Long,
    val email: String,
    val name: String?,
    val gender: String?
)

data class IdResponse(val id: Long)

data class MealRequest(
    @SerializedName("items_json") val items_json: String,
    val calories: Int?,
    @SerializedName("meal_time") val meal_time: String?
)