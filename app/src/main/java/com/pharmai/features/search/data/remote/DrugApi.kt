package com.pharmai.features.search.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface DrugApi {
    @GET("drug/label.json")
    suspend fun searchDrugs(
        @Query("search") query: String,
        @Query("limit") limit: Int = 20
    ): DrugResponse
}

data class DrugResponse(val results: List<ApiDrug>? = emptyList())
data class ApiDrug(
    val id: String?,
    val openfda: OpenFda?,
    val brand_name: List<String>?,
    val generic_name: List<String>?,
    val manufacturer_name: List<String>?,
    val purpose: List<String>?
)
data class OpenFda(
    val brand_name: List<String>?,
    val generic_name: List<String>?,
    val manufacturer_name: List<String>?
)