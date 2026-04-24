package com.pharmai.features.search.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pharmai.features.search.domain.models.Drug

@Entity(tableName = "drugs")
data class DrugEntity(
    @PrimaryKey val id: String,
    val name: String,
    val genericName: String?,
    val manufacturer: String?,
    val strength: String?,
    val description: String?,
    val indications: String,
    val sideEffects: String,
    val warnings: String
) {
    fun toDomain(isFavorite: Boolean = false) = Drug(
        id = id, name = name, genericName = genericName, manufacturer = manufacturer,
        strength = strength, description = description, isFavorite = isFavorite
    )
}