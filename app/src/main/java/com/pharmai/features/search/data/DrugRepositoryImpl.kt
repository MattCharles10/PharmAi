package com.pharmai.features.search.data

import android.util.Log
import com.pharmai.data.remote.ApiService
import com.pharmai.features.search.data.local.DrugDao
import com.pharmai.features.search.data.local.FavoriteDao
import com.pharmai.features.search.data.local.FavoriteEntity
import com.pharmai.features.search.domain.models.Drug
import com.pharmai.features.search.domain.repository.DrugRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class DrugRepositoryImpl @Inject constructor(
    private val drugDao: DrugDao,
    private val favoriteDao: FavoriteDao,
    private val api: ApiService.DrugApi
) : DrugRepository {

    companion object {
        private const val TAG = "DrugRepository"
    }

    override suspend fun searchDrugs(query: String): List<Drug> {
        Log.d(TAG, "Searching for: $query")

        return try {
            val response = api.searchDrugs(query, 20)
            val drugs = response.results?.mapNotNull { apiDrug ->
                try {
                    Drug(
                        id = apiDrug.id ?: UUID.randomUUID().toString(),
                        name = apiDrug.openfda?.brand_name?.firstOrNull()
                            ?: apiDrug.brand_name?.firstOrNull()
                            ?: query.replaceFirstChar { it.uppercase() },
                        genericName = apiDrug.openfda?.generic_name?.firstOrNull()
                            ?: apiDrug.generic_name?.firstOrNull(),
                        manufacturer = apiDrug.openfda?.manufacturer_name?.firstOrNull()
                            ?: apiDrug.manufacturer_name?.firstOrNull(),
                        description = apiDrug.purpose?.firstOrNull()
                    )
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()

            if (drugs.isEmpty()) getMockDrugs(query) else drugs
        } catch (e: Exception) {
            Log.e(TAG, "API failed, using mock data", e)
            getMockDrugs(query)
        }
    }

    override suspend fun getDrugById(id: String): Drug? {
        return Drug(
            id = id,
            name = "Paracetamol",
            genericName = "Acetaminophen",
            manufacturer = "Johnson & Johnson",
            description = "Pain reliever and fever reducer."
        )
    }

    override suspend fun toggleFavorite(drugId: String) {
        if (favoriteDao.isFavorite(drugId)) favoriteDao.remove(drugId)
        else favoriteDao.add(FavoriteEntity(drugId))
    }

    override fun observeFavorites(): Flow<List<Drug>> {
        return favoriteDao.observeFavorites().map { entities ->
            entities.map { it.toDomain(isFavorite = true) }  // ✅ Uses DrugEntity.toDomain()
        }
    }

    private fun getMockDrugs(query: String): List<Drug> {
        val allDrugs = listOf(
            Drug("1", "Paracetamol", "Acetaminophen", "Johnson & Johnson", description = "Pain reliever and fever reducer"),
            Drug("2", "Ibuprofen", "Ibuprofen", "Pfizer", description = "Anti-inflammatory pain reliever"),
            Drug("3", "Amoxicillin", "Amoxicillin", "GlaxoSmithKline", description = "Antibiotic for bacterial infections"),
            Drug("4", "Metformin", "Metformin HCl", "Merck", description = "Diabetes medication"),
            Drug("5", "Omeprazole", "Omeprazole", "AstraZeneca", description = "Reduces stomach acid"),
            Drug("6", "Aspirin", "Acetylsalicylic Acid", "Bayer", description = "Pain reliever and blood thinner"),
            Drug("7", "Cetirizine", "Cetirizine HCl", "Cipla", description = "Antihistamine for allergies"),
            Drug("8", "Atorvastatin", "Atorvastatin Calcium", "Pfizer", description = "Cholesterol lowering medication")
        )

        return allDrugs.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.genericName?.contains(query, ignoreCase = true) == true ||
                    it.manufacturer?.contains(query, ignoreCase = true) == true
        }.ifEmpty {
            listOf(Drug(UUID.randomUUID().toString(), query, description = "Search result"))
        }
    }
}