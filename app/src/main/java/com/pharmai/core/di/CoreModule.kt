package com.pharmai.core.di

import com.pharmai.features.home.domain.usecase.GetHomeDataUseCase
import com.pharmai.features.inventory.domain.repository.InventoryRepository
import com.pharmai.features.inventory.domain.usecase.*
import com.pharmai.features.prescriptions.domain.repository.PrescriptionRepository
import com.pharmai.features.prescriptions.domain.usecase.AddPrescriptionUseCase
import com.pharmai.features.prescriptions.domain.usecase.GetPrescriptionsUseCase
import com.pharmai.features.reminders.domain.repository.ReminderRepository
import com.pharmai.features.reminders.domain.usecase.*
import com.pharmai.features.scanner.domain.repository.ScannerRepository
import com.pharmai.features.scanner.domain.usecase.*
import com.pharmai.features.search.domain.repository.DrugRepository
import com.pharmai.features.search.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    // ========== SEARCH FEATURE ==========
    @Provides
    fun provideSearchDrugsUseCase(repo: DrugRepository) = SearchDrugsUseCase(repo)

    @Provides
    fun provideGetDrugDetailsUseCase(repo: DrugRepository) = GetDrugDetailsUseCase(repo)

    @Provides
    fun provideToggleFavoriteUseCase(repo: DrugRepository) = ToggleFavoriteUseCase(repo)

    // ========== INVENTORY FEATURE ==========
    @Provides
    fun provideGetInventoryUseCase(repo: InventoryRepository) = GetInventoryUseCase(repo)

    @Provides
    fun provideAddMedicineUseCase(repo: InventoryRepository) = AddMedicineUseCase(repo)

    @Provides
    fun provideUpdateMedicineUseCase(repo: InventoryRepository) = UpdateMedicineUseCase(repo)

    @Provides
    fun provideDeleteMedicineUseCase(repo: InventoryRepository) = DeleteMedicineUseCase(repo)

    @Provides
    fun provideGetExpiringMedicinesUseCase(repo: InventoryRepository) = GetExpiringMedicinesUseCase(repo)

    // ========== REMINDER FEATURE ==========
    @Provides
    fun provideGetRemindersUseCase(repo: ReminderRepository) = GetRemindersUseCase(repo)

    @Provides
    fun provideCreateReminderUseCase(repo: ReminderRepository) = CreateReminderUseCase(repo)

    @Provides
    fun provideToggleReminderUseCase(repo: ReminderRepository) = ToggleReminderUseCase(repo)

    @Provides
    fun provideDeleteReminderUseCase(repo: ReminderRepository) = DeleteReminderUseCase(repo)

    // ========== SCANNER FEATURE ==========
    @Provides
    fun provideScanMedicineUseCase(repo: ScannerRepository) = ScanMedicineUseCase(repo)

    @Provides
    fun provideRecognizeMedicineUseCase(repo: ScannerRepository) = RecognizeMedicineUseCase(repo)

    @Provides
    fun provideScanBarcodeUseCase(repo: ScannerRepository) = ScanBarcodeUseCase(repo)

    @Provides
    fun provideSaveScanUseCase(repo: ScannerRepository) = SaveScanUseCase(repo)

    @Provides
    fun provideGetScanHistoryUseCase(repo: ScannerRepository) = GetScanHistoryUseCase(repo)

    @Provides
    fun provideDeleteScanUseCase(repo: ScannerRepository) = DeleteScanUseCase(repo)

    // ========== HOME FEATURE ==========
    @Provides
    fun provideGetHomeDataUseCase() = GetHomeDataUseCase()

    // ========== PRESCRIPTION FEATURE ==========
    @Provides
    fun provideGetPrescriptionsUseCase(repo: PrescriptionRepository) = GetPrescriptionsUseCase(repo)

    @Provides
    fun provideAddPrescriptionUseCase(repo: PrescriptionRepository) = AddPrescriptionUseCase(repo)
}