package com.pharmai.domain.repository

import com.pharmai.domain.model.Drug
import kotlinx.coroutines.flow.Flow

interface DrugRepository {
    fun searchDrugs(query: String): Flow<List<Drug>>
    suspend fun searchDrugsRemote(query: String): Result<List<Drug>>
    suspend fun getDrugById(drugId: String): Drug?
    suspend fun getDrugByIdRemote(drugId: String): Result<Drug?>
    suspend fun addToFavorites(userId: String, drugId: String)
    suspend fun removeFromFavorites(userId: String, drugId: String)
    fun getFavorites(userId: String): Flow<List<Drug>>
    suspend fun isFavorite(userId: String, drugId: String): Boolean
}