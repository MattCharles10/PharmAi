package com.pharmai.features.inventory.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pharmai.features.inventory.domain.models.Medicine
import java.util.*

@Entity(tableName = "inventory")
data class InventoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val drugId: String,
    val name: String,
    val genericName: String?,
    val quantity: Int,
    val unit: String,
    val expiryDate: Date,
    val batchNumber: String?,
    val barcode: String?,
    val notes: String?
) {
    fun toDomain() = Medicine(
        id = id, drugId = drugId, name = name, genericName = genericName,
        quantity = quantity, unit = unit, expiryDate = expiryDate,
        batchNumber = batchNumber, barcode = barcode, notes = notes
    )

    companion object {
        fun fromDomain(medicine: Medicine) = InventoryEntity(
            id = medicine.id, drugId = medicine.drugId, name = medicine.name,
            genericName = medicine.genericName, quantity = medicine.quantity,
            unit = medicine.unit, expiryDate = medicine.expiryDate,
            batchNumber = medicine.batchNumber, barcode = medicine.barcode, notes = medicine.notes
        )
    }
}