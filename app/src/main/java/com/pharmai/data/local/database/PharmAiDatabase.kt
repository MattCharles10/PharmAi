package com.pharmai.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pharmai.data.local.database.converters.DateConverters
import com.pharmai.data.local.database.converters.ListConverters
import com.pharmai.data.local.database.converters.MLResultConverters
import com.pharmai.data.local.database.dao.DrugDao
import com.pharmai.data.local.database.dao.FavoriteDao
import com.pharmai.data.local.database.dao.PrescriptionDao
import com.pharmai.data.local.database.dao.ReminderDao
import com.pharmai.data.local.database.dao.ScannedMedicineDao
import com.pharmai.data.local.database.dao.SearchHistoryDao
import com.pharmai.data.local.database.dao.UserDao
import com.pharmai.data.local.database.dao.UserInventoryDao
import com.pharmai.data.model.database.DrugEntity
import com.pharmai.data.model.database.FavoriteEntity
import com.pharmai.data.model.database.PrescriptionEntity
import com.pharmai.data.model.database.ReminderEntity
import com.pharmai.data.model.database.ScannedMedicineEntity
import com.pharmai.data.model.database.SearchHistoryEntity
import com.pharmai.data.model.database.UserEntity
import com.pharmai.data.model.database.UserInventoryEntity

@Database(
    entities = [
        DrugEntity::class,
        UserInventoryEntity::class,
        PrescriptionEntity::class,
        ReminderEntity::class,
        UserEntity::class,
        ScannedMedicineEntity::class,
        SearchHistoryEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverters::class,
    ListConverters::class,
    MLResultConverters::class
)
abstract class PharmAiDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao
    abstract fun userInventoryDao(): UserInventoryDao
    abstract fun prescriptionDao(): PrescriptionDao
    abstract fun reminderDao(): ReminderDao
    abstract fun userDao(): UserDao
    abstract fun scannedMedicineDao(): ScannedMedicineDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: PharmAiDatabase? = null

        fun getDatabase(context: Context): PharmAiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PharmAiDatabase::class.java,
                    "pharmai_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}