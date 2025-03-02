package com.example.gymactive.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithToken = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${tokenProvider.token}")
            .build()
        return chain.proceed(requestWithToken)
    }
}
