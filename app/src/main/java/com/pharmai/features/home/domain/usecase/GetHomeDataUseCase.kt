package com.pharmai.features.home.domain.usecase

import com.pharmai.features.home.domain.models.ExpiringMedicine
import com.pharmai.features.home.domain.models.ReminderItem
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor() {
    suspend operator fun invoke(): HomeData {
        return HomeData(
            expiringMedicines = listOf(
                ExpiringMedicine("Paracetamol", 5),
                ExpiringMedicine("Ibuprofen", 12)
            ),
            todayReminders = listOf(
                ReminderItem("Vitamin D", "1 tablet", "09:00 AM"),
                ReminderItem("Metformin", "500mg", "08:00 PM")
            )
        )
    }
}

data class HomeData(
    val expiringMedicines: List<ExpiringMedicine>,
    val todayReminders: List<ReminderItem>
)