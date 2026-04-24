package com.pharmai.features.search.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {
    @Query("SELECT * FROM drugs WHERE id = :id")
    suspend fun getById(id: String): DrugEntity?

    @Query("SELECT * FROM drugs WHERE name LIKE '%' || :query || '%' OR genericName LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<DrugEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drug: DrugEntity)
}

@Entity(tableName = "favorites")
data class FavoriteEntity(@PrimaryKey val drugId: String)

@Dao
interface FavoriteDao {
    @Query("SELECT d.* FROM drugs d INNER JOIN favorites f ON d.id = f.drugId")
    fun observeFavorites(): Flow<List<DrugEntity>>

    @Insert suspend fun add(favorite: FavoriteEntity)
    @Query("DELETE FROM favorites WHERE drugId = :drugId") suspend fun remove(drugId: String)
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE drugId = :drugId)") suspend fun isFavorite(drugId: String): Boolean
}