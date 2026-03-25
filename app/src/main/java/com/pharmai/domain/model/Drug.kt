package com.pharmai.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Drug(
    val id: String,
    val name: String,  // Brand name or medicine name
    val brandName: String? = null,
    val genericName: String? = null,
    val manufacturer: String? = null,
    val indications: List<String> = emptyList(),
    val warnings: List<String> = emptyList(),
    val dosage: String? = null,
    val dosageForm: String? = null, // Tablet, Capsule, Liquid, etc.
    val strength: String? = null, // e.g., "500mg"
    val adverseReactions: List<String> = emptyList(),
    val drugInteractions: List<String> = emptyList(),
    val route: List<String> = emptyList(),
    val substanceName: List<String> = emptyList(),
    val imageUrl: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val requiresPrescription: Boolean = true,
    val isFavorite: Boolean = false,
    val isGeneric: Boolean = false,
    val lastUpdated: Date = Date(),
    val createdAt: Date = Date()
) : Parcelable

@Parcelize
data class DrugAlternative(
    val drug: Drug,
    val similarityScore: Float,
    val reason: String,
    val isGenericAlternative: Boolean = false,
    val priceDifference: Double? = null
) : Parcelable