// app/src/main/java/com/pharmai/core/di/NetworkModule.kt
package com.pharmai.core.di

import android.content.Context
import com.pharmai.BuildConfig
import com.pharmai.core.common.Constants
import com.pharmai.data.remote.interceptor.AuthInterceptor
import com.pharmai.data.remote.api.DrugApiService
import com.pharmai.data.remote.api.RxNormApiService
import com.pharmai.data.remote.interceptor.CacheInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = Constants.CACHE_SIZE
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        authInterceptor: AuthInterceptor,
        cacheInterceptor: CacheInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(cacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideCacheInterceptor(@ApplicationContext context: Context): CacheInterceptor {
        return CacheInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideDrugApiService(client: OkHttpClient): DrugApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DrugApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRxNormApiService(client: OkHttpClient): RxNormApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.RXNAV_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RxNormApiService::class.java)
    }
}