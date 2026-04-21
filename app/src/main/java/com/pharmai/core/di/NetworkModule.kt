package com.pharmai.core.di

import com.google.gson.GsonBuilder
import com.pharmai.core.Constants
import com.pharmai.data.remote.ApiService
import com.pharmai.data.remote.Interceptors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(Interceptors.AuthInterceptor())
        .addInterceptor(Interceptors.LoggingInterceptor())
        .connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides @Singleton
    fun provideDrugApi(client: OkHttpClient): ApiService.DrugApi {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.DrugApi::class.java)
    }
}