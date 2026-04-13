package com.pharmai.data.repository


import com.pharmai.data.local.database.dao.DrugDao
import com.pharmai.data.local.database.dao.FavoriteDao
import com.pharmai.data.model.database.DrugEntity
import com.pharmai.data.model.database.FavoriteEntity
import com.pharmai.data.remote.datasource.RemoteDataSource
import com.pharmai.domain.model.Drug
import com.pharmai.domain.repository.DrugRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrugRepositoryImpl @Inject constructor(
    private val drugDao: DrugDao,
    private val favoriteDao: FavoriteDao,
    private val remoteDataSource: RemoteDataSource
) : DrugRepository {

    override fun searchDrugs(query: String): Flow<List<Drug>> {
        return drugDao.searchDrugs(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun searchDrugsRemote(query: String): Result<List<Drug>> {
        return try {
            val response = remoteDataSource.searchDrugs(query)
            val drugs = response.results?.mapNotNull { result ->
                result.toDomainModel()
            } ?: emptyList()

            // Cache results
            drugs.forEach { drug ->
                drugDao.insertDrug(drug.toEntity())
            }

            Result.success(drugs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDrugById(drugId: String): Drug? {
        return drugDao.getDrugById(drugId)?.toDomainModel()
    }

    override suspend fun getDrugByIdRemote(drugId: String): Result<Drug?> {
        return try {
            val response = remoteDataSource.getDrugById(drugId)
            val drug = response.results?.firstNotNullOfOrNull { result ->
                result.toDomainModel()
            }
            drug?.let {
                drugDao.insertDrug(it.toEntity())
            }
            Result.success(drug)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addToFavorites(userId: String, drugId: String) {
        val favorite = FavoriteEntity(
            userId = userId,
            drugId = drugId
        )
        favoriteDao.addFavorite(favorite)
    }

    override suspend fun removeFromFavorites(userId: String, drugId: String) {
        favoriteDao.removeFavoriteByDrugId(userId, drugId)
    }

    override fun getFavorites(userId: String): Flow<List<Drug>> {
        return favoriteDao.getFavorites(userId).map { favorites ->
            favorites.mapNotNull { favorite ->
                drugDao.getDrugById(favorite.drugId)?.toDomainModel()
            }
        }
    }

    override suspend fun isFavorite(userId: String, drugId: String): Boolean {
        return favoriteDao.getFavorite(userId, drugId) != null
    }
}

// Extension functions for mapping
fun DrugEntity.toDomainModel(): Drug {
    return Drug(
        id = id,
        name = name,
        brandName = brandName,
        genericName = genericName,
        manufacturer = manufacturer,
        dosage = dosage,
        dosageForm = dosageForm,
        strength = strength,
        imageUrl = imageUrl,
        description = description,
        price = price,
        requiresPrescription = requiresPrescription,
        isGeneric = isGeneric,
        isFavorite = false
    )
}

fun Drug.toEntity(): DrugEntity {
    return DrugEntity(
        id = id,
        name = name,
        brandName = brandName,
        genericName = genericName,
        manufacturer = manufacturer,
        dosage = dosage,
        dosageForm = dosageForm,
        strength = strength,
        imageUrl = imageUrl,
        description = description,
        price = price,
        requiresPrescription = requiresPrescription,
        isGeneric = isGeneric
    )
}

fun com.pharmai.data.model.api.DrugResult.toDomainModel(): Drug? {
    return try {
        Drug(
            id = id ?: openFda?.product_ndc?.firstOrNull() ?: return null,
            name = brand_name?.firstOrNull() ?: generic_name?.firstOrNull() ?: return null,
            brandName = brand_name?.firstOrNull(),
            genericName = generic_name?.firstOrNull(),
            manufacturer = manufacturer_name?.firstOrNull(),
            indications = indications_and_usage ?: emptyList(),
            warnings = warnings ?: emptyList(),
            dosage = dosage_and_administration?.firstOrNull(),
            dosageForm = openFda?.route?.firstOrNull(),
            adverseReactions = adverse_reactions ?: emptyList(),
            drugInteractions = drug_interactions ?: emptyList(),
            route = route ?: emptyList(),
            description = description?.firstOrNull(),
            requiresPrescription = true
        )
    } catch (e: Exception) {
        null
    }
}