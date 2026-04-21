package com.pharmai.core.di

import android.content.Context
import androidx.room.Room
import com.pharmai.core.Constants
import com.pharmai.data.local.AppDatabase
import com.pharmai.data.local.Daos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context) = Room.databaseBuilder(ctx, AppDatabase::class.java, Constants.DATABASE_NAME).fallbackToDestructiveMigration().build()
    @Provides fun provideDrugDao(db: AppDatabase): Daos.DrugDao = db.drugDao()
    @Provides fun provideInventoryDao(db: AppDatabase): Daos.InventoryDao = db.inventoryDao()
    @Provides fun provideReminderDao(db: AppDatabase): Daos.ReminderDao = db.reminderDao()
    @Provides fun provideScanDao(db: AppDatabase): Daos.ScanDao = db.scanDao()
    @Provides fun provideFavoriteDao(db: AppDatabase): Daos.FavoriteDao = db.favoriteDao()
}