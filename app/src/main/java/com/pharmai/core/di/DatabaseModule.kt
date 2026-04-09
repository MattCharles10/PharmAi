
package com.pharmai.core.di

import com.pharmai.data.local.database.PharmAiDatabase
import com.pharmai.data.local.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDrugDao(database: PharmAiDatabase): DrugDao = database.drugDao()

    @Provides
    @Singleton
    fun provideUserInventoryDao(database: PharmAiDatabase): UserInventoryDao = database.userInventoryDao()

    @Provides
    @Singleton
    fun providePrescriptionDao(database: PharmAiDatabase): PrescriptionDao = database.prescriptionDao()

    @Provides
    @Singleton
    fun provideReminderDao(database: PharmAiDatabase): ReminderDao = database.reminderDao()

    @Provides
    @Singleton
    fun provideUserDao(database: PharmAiDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideScannedMedicineDao(database: PharmAiDatabase): ScannedMedicineDao = database.scannedMedicineDao()

    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: PharmAiDatabase): SearchHistoryDao = database.searchHistoryDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: PharmAiDatabase): FavoriteDao = database.favoriteDao()
}