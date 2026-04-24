package com.pharmai.core.di

import android.content.Context
import com.pharmai.core.utils.NotificationUtils
import com.pharmai.data.local.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext context: Context
    ): PreferencesManager {
        return PreferencesManager(context)
    }

    // NotificationUtils is now provided automatically via @Inject constructor
    // No need to provide it here if you added @Inject to its constructor

    // If NotificationUtils does NOT have @Inject constructor, keep this:
    @Provides
    @Singleton
    fun provideNotificationUtils(
        @ApplicationContext context: Context
    ): NotificationUtils {
        return NotificationUtils(context).apply {
            createNotificationChannels()
        }
    }
}