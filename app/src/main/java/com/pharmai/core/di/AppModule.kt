
package com.pharmai.core.di

import android.content.Context
import androidx.room.Room
import com.pharmai.data.local.database.PharmAiDatabase
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

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PharmAiDatabase {
        return PharmAiDatabase.getDatabase(context)
    }
}