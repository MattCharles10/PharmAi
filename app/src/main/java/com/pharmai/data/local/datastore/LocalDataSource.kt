package com.pharmai.data.local.datastore


import com.pharmai.data.local.database.PharmAiDatabase
import com.pharmai.data.local.database.dao.*
import com.pharmai.data.model.database.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val database: PharmAiDatabase
) {
    // Drug operations
    fun getDrugDao(): DrugDao = database.drugDao()

    // Inventory operations
    fun getInventoryDao(): UserInventoryDao = database.userInventoryDao()

    // Prescription operations
    fun getPrescriptionDao(): PrescriptionDao = database.prescriptionDao()

    // Reminder operations
    fun getReminderDao(): ReminderDao = database.reminderDao()

    // User operations
    fun getUserDao(): UserDao = database.userDao()

    // Scan history operations
    fun getScannedMedicineDao(): ScannedMedicineDao = database.scannedMedicineDao()

    // Search history operations
    fun getSearchHistoryDao(): SearchHistoryDao = database.searchHistoryDao()

    // Favorite operations
    fun getFavoriteDao(): FavoriteDao = database.favoriteDao()
}