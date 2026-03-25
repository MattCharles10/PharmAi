package com.pharmai.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class SearchHistory(
    val id: Int,
    val userId: String,
    val query: String,
    val searchType: SearchType,
    val resultCount: Int = 0,
    val searchedAt: Date = Date()
) : Parcelable

@Parcelize
enum class SearchType : Parcelable {
    DRUG_NAME,
    GENERIC_NAME,
    MANUFACTURER,
    SYMPTOM,
    BARCODE
}