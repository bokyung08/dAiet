package com.example.daiet.net

data class SignupRequest(val email: String, val password: String, val name: String?, val gender: String?)
data class LoginRequest(val email: String, val password: String)
data class TokenResponse(val access_token: String, val token_type: String)

data class MeResponse(val id: Long, val email: String, val name: String?, val gender: String?)

data class MealRequest(val items: List<String>, val calories: Int?)
data class IdResponse(val id: Long)
