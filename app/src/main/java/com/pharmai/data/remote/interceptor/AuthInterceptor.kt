package com.pharmai.data.remote.interceptor

import com.pharmai.core.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", Constants.ApiKeys.OPENFDA_API_KEY)
            .build()

        val request = original.newBuilder()
            .url(url)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}