package com.pharmai.data.local

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter fun fromDate(date: Date?) = date?.time
    @TypeConverter fun toDate(time: Long?) = time?.let { Date(it) }
}