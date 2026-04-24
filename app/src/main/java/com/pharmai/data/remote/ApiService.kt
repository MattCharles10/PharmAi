package com.pharmai.data.remote

object ApiService {
    interface DrugApi {
        @retrofit2.http.GET("drug/label.json")
        suspend fun searchDrugs(@retrofit2.http.Query("search") query: String, @retrofit2.http.Query("limit") limit: Int = 20): DrugResponse
    }
}