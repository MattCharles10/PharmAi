package com.pharmai.data.model.api


data class SearchRequest(
    val query: String,
    val limit: Int = 20,
    val skip: Int = 0
)