package com.pharmai.data.remote

data class DrugResponse(val results: List<ApiDrug>? = emptyList())
data class ApiDrug(val id: String?, val openfda: OpenFda?, val brand_name: List<String>?, val generic_name: List<String>?, val manufacturer_name: List<String>?, val purpose: List<String>?)
data class OpenFda(val brand_name: List<String>?, val generic_name: List<String>?, val manufacturer_name: List<String>?)