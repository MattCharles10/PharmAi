
package com.pharmai.data.model.api

import com.google.gson.annotations.SerializedName

data class DrugResponse(
    val meta: Meta? = null,
    val results: List<DrugResult>? = null
)

data class Meta(
    val disclaimer: String? = null,
    val terms: String? = null,
    val license: String? = null,
    val lastUpdated: String? = null,
    val results: ResultInfo? = null
)

data class ResultInfo(
    val skip: Int = 0,
    val limit: Int = 0,
    val total: Int = 0
)

data class DrugResult(
    val id: String? = null,
    @SerializedName("openfda")
    val openFda: OpenFda? = null,
    val indications_and_usage: List<String>? = null,
    val warnings: List<String>? = null,
    val dosage_and_administration: List<String>? = null,
    val adverse_reactions: List<String>? = null,
    val drug_interactions: List<String>? = null,
    val description: List<String>? = null,
    val route: List<String>? = null,
    val brand_name: List<String>? = null,
    val generic_name: List<String>? = null,
    val manufacturer_name: List<String>? = null
)

data class OpenFda(
    val brand_name: List<String>? = null,
    val generic_name: List<String>? = null,
    val manufacturer_name: List<String>? = null,
    val substance_name: List<String>? = null,
    val route: List<String>? = null,
    val product_ndc: List<String>? = null,
    val application_number: List<String>? = null
)