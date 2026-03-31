
package com.pharmai.data.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pharmai.domain.model.DetectedFeatures

class MLResultConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromDetectedFeatures(features: DetectedFeatures?): String? {
        return features?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toDetectedFeatures(json: String?): DetectedFeatures? {
        return json?.let { gson.fromJson(it, DetectedFeatures::class.java) }
    }
}