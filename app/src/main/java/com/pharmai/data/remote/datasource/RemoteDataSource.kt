package com.pharmai.data.remote.datasource

import com.pharmai.data.model.api.DrugResponse
import com.pharmai.data.remote.api.DrugApiService
import com.pharmai.data.remote.api.RxNormApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val drugApiService: DrugApiService,
    private val rxNormApiService: RxNormApiService
) {
    suspend fun searchDrugs(query: String, limit: Int = 20, skip: Int = 0): DrugResponse {
        val searchQuery = "brand_name:$query+OR+generic_name:$query"
        return drugApiService.searchDrugs(searchQuery, limit, skip)
    }

    suspend fun getDrugById(id: String): DrugResponse {
        val searchQuery = "id:$id"
        return drugApiService.getDrugById(searchQuery)
    }

    suspend fun searchAlternatives(drugName: String) = rxNormApiService.searchDrugs(drugName)
}