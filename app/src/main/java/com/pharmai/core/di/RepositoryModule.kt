package com.pharmai.core.di

import com.pharmai.data.local.Daos
import com.pharmai.data.remote.ApiService
import com.pharmai.data.repository.*
import com.pharmai.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides @Singleton fun provideDrugRepository(drugDao: Daos.DrugDao, favDao: Daos.FavoriteDao, api: ApiService.DrugApi): DrugRepository = DrugRepositoryImpl(drugDao, favDao, api)
    @Provides @Singleton fun provideInventoryRepository(invDao: Daos.InventoryDao, api: ApiService.DrugApi): InventoryRepository = InventoryRepositoryImpl(invDao, api)
    @Provides @Singleton fun provideReminderRepository(remDao: Daos.ReminderDao): ReminderRepository = ReminderRepositoryImpl(remDao)
    @Provides @Singleton fun provideMLRepository(scanDao: Daos.ScanDao): MLRepository = MLRepositoryImpl(scanDao)
}