package com.pharmai.core.di

import android.content.Context
import androidx.room.Room
import com.pharmai.core.utils.Constants
import com.pharmai.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext ctx: Context
    ): AppDatabase = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java,
        Constants.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides fun provideDrugDao(db: AppDatabase) = db.drugDao()
    @Provides fun provideFavoriteDao(db: AppDatabase) = db.favoriteDao()
    @Provides fun provideInventoryDao(db: AppDatabase) = db.inventoryDao()
    @Provides fun provideReminderDao(db: AppDatabase) = db.reminderDao()
    @Provides fun provideScanDao(db: AppDatabase) = db.scanDao()

    @Provides fun providePrescriptionDao(db: AppDatabase) = db.prescriptionDao()
}