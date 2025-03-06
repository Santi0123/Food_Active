package com.example.gymactive.di

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = sharedPreferences.getString("token",null)

        tokenProvider.token = token
        if(!token.isNullOrBlank()){
            val requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${token}")
                .build()
            return chain.proceed(requestWithToken)
        }else{
            return chain.proceed(originalRequest)
        }
    }
}
