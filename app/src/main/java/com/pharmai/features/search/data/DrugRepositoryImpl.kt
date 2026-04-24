package com.pharmai.features.search.data

import com.pharmai.data.remote.ApiService
import com.pharmai.features.search.data.local.DrugDao
import com.pharmai.features.search.data.local.DrugEntity
import com.pharmai.features.search.data.local.FavoriteDao
import com.pharmai.features.search.data.local.FavoriteEntity
//import com.pharmai.features.search.data.remote.DrugApi
import com.pharmai.features.search.domain.models.Drug
import com.pharmai.features.search.domain.repository.DrugRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class DrugRepositoryImpl @Inject constructor(
    private val drugDao: DrugDao,
    private val favoriteDao: FavoriteDao,
    private val api: ApiService.DrugApi
) : DrugRepository {

    override suspend fun searchDrugs(query: String): List<Drug> {
        return try {
            api.searchDrugs(query).results?.map { apiDrug ->
                Drug(
                    id = apiDrug.id ?: UUID.randomUUID().toString(),
                    name = apiDrug.openfda?.brand_name?.firstOrNull() ?: "Unknown",
                    genericName = apiDrug.openfda?.generic_name?.firstOrNull(),
                    manufacturer = apiDrug.openfda?.manufacturer_name?.firstOrNull(),
                    description = apiDrug.purpose?.firstOrNull()
                )
            } ?: emptyList()
        } catch (e: Exception) {
            drugDao.search("%$query%").map { it.toDomain() }
        }
    }

    override suspend fun getDrugById(id: String): Drug? {
        return drugDao.getById(id)?.toDomain(favoriteDao.isFavorite(id))
    }

    override suspend fun toggleFavorite(drugId: String) {
        if (favoriteDao.isFavorite(drugId)) favoriteDao.remove(drugId)
        else favoriteDao.add(FavoriteEntity(drugId))
    }

    override fun observeFavorites(): Flow<List<Drug>> {
        return favoriteDao.observeFavorites().map { entities ->
            entities.map { it.toDomain(true) }
        }
    }
}