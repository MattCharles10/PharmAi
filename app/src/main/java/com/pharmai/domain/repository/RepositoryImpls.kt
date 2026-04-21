package com.pharmai.data.repository

import com.pharmai.data.local.Daos
import com.pharmai.data.local.Entities
import com.pharmai.data.remote.ApiService
import com.pharmai.domain.models.*
import com.pharmai.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

// Drug Repository Implementation
class DrugRepositoryImpl @Inject constructor(
    private val drugDao: Daos.DrugDao,
    private val favoriteDao: Daos.FavoriteDao,
    private val api: ApiService.DrugApi
) : DrugRepository {

    override suspend fun searchDrugs(query: String): List<Drug> {
        return try {
            val localDrugs = drugDao.search(query)
            if (localDrugs.isNotEmpty()) {
                return localDrugs.map { entity ->
                    Drug(
                        id = entity.id,
                        name = entity.name,
                        genericName = entity.genericName,
                        manufacturer = entity.manufacturer,
                        description = entity.description,
                        isFavorite = favoriteDao.isFavorite(entity.id)
                    )
                }
            }

            val response = api.searchDrugs(query)
            response.results?.map { apiDrug ->
                Drug(
                    id = apiDrug.id ?: UUID.randomUUID().toString(),
                    name = apiDrug.openfda?.brand_name?.firstOrNull() ?: "Unknown",
                    genericName = apiDrug.openfda?.generic_name?.firstOrNull(),
                    manufacturer = apiDrug.openfda?.manufacturer_name?.firstOrNull(),
                    description = apiDrug.purpose?.firstOrNull()
                )
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getDrugById(id: String): Drug? {
        return drugDao.getById(id)?.let { entity ->
            Drug(
                id = entity.id,
                name = entity.name,
                genericName = entity.genericName,
                manufacturer = entity.manufacturer,
                description = entity.description,
                isFavorite = favoriteDao.isFavorite(entity.id)
            )
        }
    }

    override suspend fun toggleFavorite(drugId: String) {
        if (favoriteDao.isFavorite(drugId)) {
            favoriteDao.remove(drugId)
        } else {
            favoriteDao.add(Entities.FavoriteEntity(drugId))
        }
    }

    override fun observeFavorites(): Flow<List<Drug>> {
        return favoriteDao.observeFavorites().map { entities ->
            entities.map { entity ->
                Drug(
                    id = entity.id,
                    name = entity.name,
                    genericName = entity.genericName,
                    manufacturer = entity.manufacturer,
                    description = entity.description,
                    isFavorite = true
                )
            }
        }
    }
}

// Inventory Repository Implementation
class InventoryRepositoryImpl @Inject constructor(
    private val dao: Daos.InventoryDao,
    private val api: ApiService.DrugApi
) : InventoryRepository {

    override fun observeAll(): Flow<List<UserInventory>> {
        return dao.observeAll().map { entities ->
            entities.map { entity ->
                UserInventory(
                    id = entity.id,
                    drugId = entity.drugId,
                    drugName = entity.drugName,
                    quantity = entity.quantity,
                    unit = entity.unit,
                    expiryDate = entity.expiryDate,
                    barcode = entity.barcode,
                    notes = entity.notes
                )
            }
        }
    }

    override suspend fun getAll(): List<UserInventory> {
        return dao.getAll().map { entity ->
            UserInventory(
                id = entity.id,
                drugId = entity.drugId,
                drugName = entity.drugName,
                quantity = entity.quantity,
                unit = entity.unit,
                expiryDate = entity.expiryDate,
                barcode = entity.barcode,
                notes = entity.notes
            )
        }
    }

    override suspend fun getById(id: Long): UserInventory? {
        return dao.getById(id)?.let { entity ->
            UserInventory(
                id = entity.id,
                drugId = entity.drugId,
                drugName = entity.drugName,
                quantity = entity.quantity,
                unit = entity.unit,
                expiryDate = entity.expiryDate,
                barcode = entity.barcode,
                notes = entity.notes
            )
        }
    }

    override suspend fun add(item: UserInventory): Long {
        val entity = Entities.InventoryEntity(
            drugId = item.drugId,
            drugName = item.drugName,
            quantity = item.quantity,
            unit = item.unit,
            expiryDate = item.expiryDate,
            barcode = item.barcode,
            notes = item.notes
        )
        return dao.insert(entity)
    }

    override suspend fun update(item: UserInventory) {
        val entity = Entities.InventoryEntity(
            id = item.id,
            drugId = item.drugId,
            drugName = item.drugName,
            quantity = item.quantity,
            unit = item.unit,
            expiryDate = item.expiryDate,
            barcode = item.barcode,
            notes = item.notes
        )
        dao.update(entity)
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun getExpiring(threshold: Int): List<UserInventory> {
        val now = Date()
        val thresholdDate = Calendar.getInstance().apply {
            time = now
            add(Calendar.DAY_OF_YEAR, threshold)
        }.time

        return dao.getExpiring(now, thresholdDate).map { entity ->
            UserInventory(
                id = entity.id,
                drugId = entity.drugId,
                drugName = entity.drugName,
                quantity = entity.quantity,
                unit = entity.unit,
                expiryDate = entity.expiryDate,
                barcode = entity.barcode,
                notes = entity.notes
            )
        }
    }

    override suspend fun getExpired(): List<UserInventory> {
        return dao.getExpired(Date()).map { entity ->
            UserInventory(
                id = entity.id,
                drugId = entity.drugId,
                drugName = entity.drugName,
                quantity = entity.quantity,
                unit = entity.unit,
                expiryDate = entity.expiryDate,
                barcode = entity.barcode,
                notes = entity.notes
            )
        }
    }

    override suspend fun getByBarcode(barcode: String): UserInventory? {
        return dao.getByBarcode(barcode)?.let { entity ->
            UserInventory(
                id = entity.id,
                drugId = entity.drugId,
                drugName = entity.drugName,
                quantity = entity.quantity,
                unit = entity.unit,
                expiryDate = entity.expiryDate,
                barcode = entity.barcode,
                notes = entity.notes
            )
        }
    }
}

// Reminder Repository Implementation
class ReminderRepositoryImpl @Inject constructor(
    private val dao: Daos.ReminderDao
) : ReminderRepository {

    override fun observeActive(): Flow<List<Reminder>> {
        return dao.observeActive().map { entities ->
            entities.map { entity ->
                Reminder(
                    id = entity.id,
                    medicineName = entity.medicineName,
                    dosage = entity.dosage,
                    timeHour = entity.timeHour,
                    timeMinute = entity.timeMinute,
                    isActive = entity.isActive,
                    daysOfWeek = entity.daysOfWeek?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
                )
            }
        }
    }

    override suspend fun getAll(): List<Reminder> {
        return dao.getAll().map { entity ->
            Reminder(
                id = entity.id,
                medicineName = entity.medicineName,
                dosage = entity.dosage,
                timeHour = entity.timeHour,
                timeMinute = entity.timeMinute,
                isActive = entity.isActive,
                daysOfWeek = entity.daysOfWeek?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
            )
        }
    }

    override suspend fun getById(id: Long): Reminder? {
        return dao.getById(id)?.let { entity ->
            Reminder(
                id = entity.id,
                medicineName = entity.medicineName,
                dosage = entity.dosage,
                timeHour = entity.timeHour,
                timeMinute = entity.timeMinute,
                isActive = entity.isActive,
                daysOfWeek = entity.daysOfWeek?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
            )
        }
    }

    override suspend fun create(reminder: Reminder): Long {
        val entity = Entities.ReminderEntity(
            medicineName = reminder.medicineName,
            dosage = reminder.dosage,
            timeHour = reminder.timeHour,
            timeMinute = reminder.timeMinute,
            isActive = reminder.isActive,
            daysOfWeek = reminder.daysOfWeek.joinToString(",")
        )
        return dao.insert(entity)
    }

    override suspend fun update(reminder: Reminder) {
        val entity = Entities.ReminderEntity(
            id = reminder.id,
            medicineName = reminder.medicineName,
            dosage = reminder.dosage,
            timeHour = reminder.timeHour,
            timeMinute = reminder.timeMinute,
            isActive = reminder.isActive,
            daysOfWeek = reminder.daysOfWeek.joinToString(",")
        )
        dao.update(entity)
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun toggle(id: Long, isActive: Boolean) {
        dao.toggle(id, isActive)
    }
}

// Prescription Repository Implementation
class PrescriptionRepositoryImpl @Inject constructor(
    private val dao: Daos.PrescriptionDao
) : PrescriptionRepository {

    override fun observeAll(): Flow<List<Prescription>> {
        return dao.observeAll().map { entities ->
            entities.map { entity ->
                Prescription(
                    id = entity.id,
                    doctorName = entity.doctorName,
                    prescriptionDate = entity.prescriptionDate,
                    drugName = entity.drugName,
                    dosage = entity.dosage,
                    frequency = entity.frequency,
                    instructions = entity.instructions,
                    isActive = entity.isActive
                )
            }
        }
    }

    override suspend fun add(prescription: Prescription): Long {
        val entity = Entities.PrescriptionEntity(
            doctorName = prescription.doctorName,
            prescriptionDate = prescription.prescriptionDate,
            drugName = prescription.drugName,
            dosage = prescription.dosage,
            frequency = prescription.frequency,
            instructions = prescription.instructions,
            isActive = prescription.isActive
        )
        return dao.insert(entity)
    }

    override suspend fun update(prescription: Prescription) {
        val entity = Entities.PrescriptionEntity(
            id = prescription.id,
            doctorName = prescription.doctorName,
            prescriptionDate = prescription.prescriptionDate,
            drugName = prescription.drugName,
            dosage = prescription.dosage,
            frequency = prescription.frequency,
            instructions = prescription.instructions,
            isActive = prescription.isActive
        )
        dao.update(entity)
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }
}

// ML Repository Implementation
class MLRepositoryImpl @Inject constructor(
    private val dao: Daos.ScanDao
) : MLRepository {

    override fun observeScans(): Flow<List<ScannedMedicine>> {
        return dao.observeAll().map { entities ->
            entities.map { entity ->
                ScannedMedicine(
                    id = entity.id,
                    medicineName = entity.medicineName,
                    confidence = entity.confidence,
                    scanType = try {
                        ScanType.valueOf(entity.scanType)
                    } catch (e: Exception) {
                        ScanType.PILL
                    },
                    imageUri = entity.imageUri,
                    barcode = entity.barcode,
                    scanDate = entity.scanDate
                )
            }
        }
    }

    override suspend fun saveScan(scan: ScannedMedicine) {
        val entity = Entities.ScanEntity(
            id = scan.id,
            medicineName = scan.medicineName,
            confidence = scan.confidence,
            scanType = scan.scanType.name,
            imageUri = scan.imageUri,
            barcode = scan.barcode,
            scanDate = scan.scanDate
        )
        dao.insert(entity)
    }

    override suspend fun getScanById(id: String): ScannedMedicine? {
        return dao.getById(id)?.let { entity ->
            ScannedMedicine(
                id = entity.id,
                medicineName = entity.medicineName,
                confidence = entity.confidence,
                scanType = try {
                    ScanType.valueOf(entity.scanType)
                } catch (e: Exception) {
                    ScanType.PILL
                },
                imageUri = entity.imageUri,
                barcode = entity.barcode,
                scanDate = entity.scanDate
            )
        }
    }

    override suspend fun deleteScan(id: String) {
        dao.delete(id)
    }
}