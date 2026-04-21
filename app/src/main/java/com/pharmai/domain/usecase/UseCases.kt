package com.pharmai.domain.usecase

import android.graphics.Bitmap
import com.pharmai.domain.models.*
import com.pharmai.domain.repository.*
import com.pharmai.ml.MedicineClassifier
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInventoryUseCase @Inject constructor(private val repo: InventoryRepository) {
    operator fun invoke(): Flow<List<UserInventory>> = repo.observeAll()
}
class AddToInventoryUseCase @Inject constructor(private val repo: InventoryRepository) {
    suspend operator fun invoke(item: UserInventory): Long = repo.add(item)
}
class DeleteInventoryUseCase @Inject constructor(private val repo: InventoryRepository) {
    suspend operator fun invoke(id: Long) = repo.delete(id)
}
class GetExpiringMedicinesUseCase @Inject constructor(private val repo: InventoryRepository) {
    suspend operator fun invoke(threshold: Int = 30) = repo.getExpiring(threshold)
}

class GetRemindersUseCase @Inject constructor(private val repo: ReminderRepository) {
    operator fun invoke(): Flow<List<Reminder>> = repo.observeActive()
}
class CreateReminderUseCase @Inject constructor(private val repo: ReminderRepository) {
    suspend operator fun invoke(reminder: Reminder): Long = repo.create(reminder)
}
class ToggleReminderUseCase @Inject constructor(private val repo: ReminderRepository) {
    suspend operator fun invoke(id: Long, isActive: Boolean) = repo.toggle(id, isActive)
}

class SearchDrugsUseCase @Inject constructor(private val repo: DrugRepository) {
    suspend operator fun invoke(query: String) = repo.searchDrugs(query)
}
class ToggleFavoriteUseCase @Inject constructor(private val repo: DrugRepository) {
    suspend operator fun invoke(drugId: String) = repo.toggleFavorite(drugId)
}

class RecognizeMedicineUseCase @Inject constructor(private val repo: MLRepository, private val classifier: MedicineClassifier) {
    suspend operator fun invoke(bitmap: Bitmap): ScannedMedicine {
        val result = classifier.classify(bitmap)
        val scan = ScannedMedicine(medicineName = result.medicineName, confidence = result.confidence, scanType = result.scanType)
        repo.saveScan(scan)
        return scan
    }
}
class GetScanHistoryUseCase @Inject constructor(private val repo: MLRepository) {
    operator fun invoke(): Flow<List<ScannedMedicine>> = repo.observeScans()
}