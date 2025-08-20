package com.example.daiet.net

import android.content.Context
import com.example.daiet.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Response as RResponse

/** ---------- Retrofit API ---------- */
interface ApiService {
    @POST("/auth/signup") suspend fun signup(@Body body: SignupRequest): RResponse<Map<String, Any>>
    @POST("/auth/login")  suspend fun login(@Body body: LoginRequest): TokenResponse
    @GET("/auth/me")      suspend fun me(): MeResponse

    @POST("/meals")       suspend fun addMeal(@Body body: MealRequest): IdResponse
    @GET("/meals/recent") suspend fun recentMeals(@Query("limit") limit: Int = 5): List<Map<String, Any>>

    @POST("/chronic")     suspend fun addChronic(@Body body: MutableMap<String, Any>): Map<String, Any>
    @GET("/chronic")      suspend fun listChronic(): List<Map<String, Any>>

    @GET("/recommend/meal") suspend fun recommendMeal(): Map<String, Any>
}

/** ---------- Builder ---------- */
object ApiBuilder {
    fun create(context: Context): ApiService {
        val logging = HttpLoggingInterceptor().apply {
            // 개발 중엔 BODY 로그가 편함
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(TokenInterceptor(context))
            .build()

        val gson = GsonBuilder().create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // ex) http://10.0.2.2:8000
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}
