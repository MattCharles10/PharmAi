package com.pharmai.core.di

import android.content.Context
import com.pharmai.core.BiometricUtils
import com.pharmai.core.NotificationUtils
//import com.pharmai.data.local.PreferencesManager
import com.pharmai.data.local.datastore.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton fun providePreferencesManager(@ApplicationContext ctx: Context) =
        PreferencesManager(ctx)
    @Provides @Singleton fun provideNotificationUtils(@ApplicationContext ctx: Context) = NotificationUtils(ctx).apply { createChannels() }
    @Provides @Singleton fun provideBiometricUtils(@ApplicationContext ctx: Context) = BiometricUtils(ctx)
}