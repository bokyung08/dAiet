package com.example.daiet.net

import android.content.Context
import com.example.daiet.BuildConfig
import com.example.daiet.auth.TokenStore
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response as RResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { TokenStore.get(context) }
        val req = if (!token.isNullOrBlank())
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        else chain.request()
        return chain.proceed(req)
    }
}

interface ApiService {
    @POST("/auth/signup") suspend fun signup(@Body body: SignupRequest): RResponse<Map<String, Any>>
    @POST("/auth/login")  suspend fun login(@Body body: LoginRequest): TokenResponse
    @GET("/auth/me")      suspend fun me(): MeResponse

    @POST("/meals")       suspend fun addMeal(@Body body: MealRequest): IdResponse
    @GET("/meals/recent") suspend fun recentMeals(@Query("limit") limit: Int = 5): List<Map<String, Any>>

    @POST("/chronic")     suspend fun addChronic(@Body body: Map<String, String>): Map<String, Any>
    @GET("/chronic")      suspend fun listChronic(): List<Map<String, Any>>

    @GET("/recommend/meal") suspend fun recommendMeal(): Map<String, Any>
}

object ApiBuilder {
    fun create(context: Context): ApiService {
        val logging = HttpLoggingInterceptor().apply {
            // 개발용: BODY로 요청/응답 전체 확인
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}
