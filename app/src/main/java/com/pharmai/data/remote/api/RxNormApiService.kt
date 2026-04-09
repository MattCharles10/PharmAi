
package com.pharmai.data.remote.api

import com.pharmai.data.model.api.RxNormResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RxNormApiService {
    @GET("drugs.json?name={drugName}")
    suspend fun searchDrugs(
        @Path("drugName") drugName: String
    ): RxNormResponse

    @GET("rxcui/{rxcui}/related.json?tty=IN")
    suspend fun getAlternatives(
        @Path("rxcui") rxcui: String
    ): RxNormResponse
}