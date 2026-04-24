package com.pharmai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pharmai.features.inventory.data.local.InventoryEntity
import com.pharmai.features.prescriptions.data.local.PrescriptionEntity
import com.pharmai.features.reminders.data.local.ReminderEntity
import com.pharmai.features.scanner.data.local.ScanEntity
import com.pharmai.features.search.data.local.DrugEntity
import com.pharmai.features.search.data.local.FavoriteEntity

@Database(
    entities = [
        DrugEntity::class,
        FavoriteEntity::class,
        InventoryEntity::class,
        ReminderEntity::class,
        ScanEntity::class,
        PrescriptionEntity::class  // ADD THIS
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drugDao(): com.pharmai.features.search.data.local.DrugDao
    abstract fun favoriteDao(): com.pharmai.features.search.data.local.FavoriteDao
    abstract fun inventoryDao(): com.pharmai.features.inventory.data.local.InventoryDao
    abstract fun reminderDao(): com.pharmai.features.reminders.data.local.ReminderDao
    abstract fun scanDao(): com.pharmai.features.scanner.data.local.ScanDao
    abstract fun prescriptionDao(): com.pharmai.features.prescriptions.data.local.PrescriptionDao  // ADD THIS
}