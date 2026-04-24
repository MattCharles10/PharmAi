package com.pharmai.features.search.domain.repository

import com.pharmai.features.search.domain.models.Drug
import kotlinx.coroutines.flow.Flow

interface DrugRepository {
    suspend fun searchDrugs(query: String): List<Drug>
    suspend fun getDrugById(id: String): Drug?
    suspend fun toggleFavorite(drugId: String)
    fun observeFavorites(): Flow<List<Drug>>
}