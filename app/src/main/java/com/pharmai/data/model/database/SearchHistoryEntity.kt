package com.pharmai.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val query: String,
    val searchType: String, // DRUG_NAME, GENERIC_NAME, MANUFACTURER, SYMPTOM, BARCODE
    val resultCount: Int = 0,
    val searchedAt: Date = Date()
)