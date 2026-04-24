package com.pharmai.features.search.domain.models

data class Drug(
    val id: String,
    val name: String,
    val genericName: String? = null,
    val manufacturer: String? = null,
    val strength: String? = null,
    val description: String? = null,
    val indications: List<String> = emptyList(),
    val sideEffects: List<String> = emptyList(),
    val warnings: List<String> = emptyList(),
    val isFavorite: Boolean = false
)