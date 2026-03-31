
package com.pharmai.data.model.api

import com.google.gson.annotations.SerializedName

data class RxNormResponse(
    @SerializedName("minConceptGroup")
    val minConceptGroup: MinConceptGroup? = null
)

data class MinConceptGroup(
    @SerializedName("minConcept")
    val minConcept: List<MinConcept>? = null
)

data class MinConcept(
    @SerializedName("rxcui")
    val rxcui: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("tty")
    val tty: String? = null
)