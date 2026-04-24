package com.pharmai.features.home.domain.models

data class ExpiringMedicine(val name: String, val daysLeft: Int)
data class ReminderItem(val medicineName: String, val dosage: String, val time: String)