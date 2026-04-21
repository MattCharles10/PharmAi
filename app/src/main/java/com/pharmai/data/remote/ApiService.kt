package com.pharmai.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

object ApiService {
    interface DrugApi {
        @GET("drug/label.json")
        suspend fun searchDrugs(@Query("search") query: String, @Query("limit") limit: Int = 20): DrugResponse
    }
}