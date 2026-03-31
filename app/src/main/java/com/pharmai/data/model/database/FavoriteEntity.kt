package com.pharmai.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val drugId: String,
    val notes: String? = null,
    val createdAt: Date = Date()
)