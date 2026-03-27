package com.pharmai.data.model.database

import androidx.compose.foundation.interaction.Interaction
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pharmai.domain.model.Prescription
import java.util.Date


@Entity(tableName =  "drugs")
data class DrugEntity(

    @PrimaryKey
    val id: String,
    val name: String,
    val brandName: String? = null,
    val genericName: String? = null,
    val manufacturer: String? = null,
    val indications: String? = null, // Stored as JSON
    val warnings: String? = null, // Stored as JSON
    val dosage: String? = null,
    val dosageForm: String? = null,
    val strength: String? = null,
    val adverseReactions: String? = null, // Stored as JSON
    val drugInteractions: String? = null, // Stored as JSON
    val route: String? = null, // Stored as JSON
    val substanceName: String? = null, // Stored as JSON
    val imageUrl: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val requiresPrescription: Boolean = true,
    val isGeneric: Boolean = false,
    val lastUpdated: Date = Date(),
    val createdAt: Date = Date()


)