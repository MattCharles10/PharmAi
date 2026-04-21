package com.pharmai.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

object Interceptors {
    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder().addHeader("Content-Type", "application/json").build()
            return chain.proceed(request)
        }
    }
    class LoggingInterceptor : Interceptor {
        private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        override fun intercept(chain: Interceptor.Chain) = logger.intercept(chain)
    }
}