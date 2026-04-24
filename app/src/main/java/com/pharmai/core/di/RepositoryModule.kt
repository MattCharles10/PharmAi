package com.pharmai.core.di

import com.google.gson.Gson
import com.pharmai.data.remote.ApiService
import com.pharmai.features.auth.data.AuthRepositoryImpl
import com.pharmai.features.auth.domain.repository.AuthRepository
import com.pharmai.features.inventory.data.InventoryRepositoryImpl
import com.pharmai.features.inventory.data.local.InventoryDao
import com.pharmai.features.reminders.data.ReminderRepositoryImpl
import com.pharmai.features.reminders.data.local.ReminderDao
import com.pharmai.features.scanner.data.ScannerRepositoryImpl
import com.pharmai.features.scanner.data.local.ScanDao
import com.pharmai.features.search.data.DrugRepositoryImpl
import com.pharmai.features.search.data.local.DrugDao
import com.pharmai.features.search.data.local.FavoriteDao
import com.pharmai.features.inventory.domain.repository.InventoryRepository
import com.pharmai.features.reminders.domain.repository.ReminderRepository
import com.pharmai.features.scanner.domain.repository.ScannerRepository
import com.pharmai.features.search.domain.repository.DrugRepository
import com.pharmai.features.prescriptions.data.PrescriptionRepositoryImpl
import com.pharmai.features.prescriptions.data.local.PrescriptionDao
import com.pharmai.features.prescriptions.domain.repository.PrescriptionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDrugRepository(
        drugDao: DrugDao,
        favoriteDao: FavoriteDao,
        api: ApiService.DrugApi
    ): DrugRepository = DrugRepositoryImpl(drugDao, favoriteDao, api)

    @Provides
    @Singleton
    fun provideInventoryRepository(
        inventoryDao: InventoryDao
    ): InventoryRepository = InventoryRepositoryImpl(inventoryDao)

    @Provides
    @Singleton
    fun provideReminderRepository(
        reminderDao: ReminderDao
    ): ReminderRepository = ReminderRepositoryImpl(reminderDao)

    @Provides
    @Singleton
    fun provideScannerRepository(
        scanDao: ScanDao,
        medicineClassifier: com.pharmai.features.scanner.ml.MedicineClassifier,
        barcodeScanner: com.pharmai.features.scanner.ml.BarcodeScanner,
        expiryDetector: com.pharmai.features.scanner.ml.ExpiryDateDetector
    ): ScannerRepository = ScannerRepositoryImpl(medicineClassifier, barcodeScanner, expiryDetector, scanDao)

    @Provides
    @Singleton
    fun providePrescriptionRepository(
        prescriptionDao: PrescriptionDao,
        gson: Gson
    ): PrescriptionRepository = PrescriptionRepositoryImpl(prescriptionDao, gson)

    // ADD THIS - AuthRepository
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()
}