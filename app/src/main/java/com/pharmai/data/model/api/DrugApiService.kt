package com.pharmai.data.model.api


import com.pharmai.data.model.api.DrugResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DrugApiService {
    @GET("drug/label.json")
    suspend fun searchDrugs(
        @Query("search") search: String,
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): DrugResponse

    @GET("drug/label.json")
    suspend fun getDrugById(
        @Query("search") id: String
    ): DrugResponse

    @GET("drug/label.json")
    suspend fun getDrugByManufacturer(
        @Query("search") manufacturer: String,
        @Query("limit") limit: Int = 20
    ): DrugResponse
}