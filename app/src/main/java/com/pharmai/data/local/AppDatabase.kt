package com.pharmai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Entities.DrugEntity::class, Entities.InventoryEntity::class, Entities.ReminderEntity::class, Entities.ScanEntity::class, Entities.FavoriteEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drugDao(): Daos.DrugDao
    abstract fun inventoryDao(): Daos.InventoryDao
    abstract fun reminderDao(): Daos.ReminderDao
    abstract fun scanDao(): Daos.ScanDao
    abstract fun favoriteDao(): Daos.FavoriteDao
}