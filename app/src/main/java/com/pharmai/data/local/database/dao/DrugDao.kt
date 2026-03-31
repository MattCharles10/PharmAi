package com.pharmai.data.local.database.dao

import androidx.room.*
import com.pharmai.data.model.database.DrugEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {
    @Query("SELECT * FROM drugs WHERE id = :drugId")
    suspend fun getDrugById(drugId: String): DrugEntity?

    @Query("SELECT * FROM drugs WHERE name LIKE '%' || :query || '%' OR genericName LIKE '%' || :query || '%'")
    fun searchDrugs(query: String): Flow<List<DrugEntity>>

    @Query("SELECT * FROM drugs WHERE manufacturer LIKE '%' || :manufacturer || '%'")
    fun getDrugsByManufacturer(manufacturer: String): Flow<List<DrugEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrug(drug: DrugEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrugs(drugs: List<DrugEntity>)

    @Update
    suspend fun updateDrug(drug: DrugEntity)

    @Delete
    suspend fun deleteDrug(drug: DrugEntity)

    @Query("DELETE FROM drugs")
    suspend fun deleteAllDrugs()

    @Query("SELECT * FROM drugs WHERE isGeneric = 1")
    fun getGenericDrugs(): Flow<List<DrugEntity>>
}