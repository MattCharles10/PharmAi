package com.pharmai.features.search.domain.usecase

import com.pharmai.features.search.domain.models.Drug
import com.pharmai.features.search.domain.repository.DrugRepository
import javax.inject.Inject

class SearchDrugsUseCase @Inject constructor(private val repository: DrugRepository) {
    suspend operator fun invoke(query: String): List<Drug> = repository.searchDrugs(query)
}

class GetDrugDetailsUseCase @Inject constructor(private val repository: DrugRepository) {
    suspend operator fun invoke(id: String): Drug? = repository.getDrugById(id)
}

class ToggleFavoriteUseCase @Inject constructor(private val repository: DrugRepository) {
    suspend operator fun invoke(drugId: String) = repository.toggleFavorite(drugId)
}