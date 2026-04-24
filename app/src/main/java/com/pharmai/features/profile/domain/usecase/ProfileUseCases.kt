package com.pharmai.features.profile.domain.usecase

import com.pharmai.features.profile.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor() {
    operator fun invoke(): Flow<UserProfile> = flow {
        emit(
            UserProfile(
                id = "1",
                name = "Sarah Wilson",
                email = "sarah.wilson@email.com",
                phoneNumber = "+1 234 567 8900",
                dateOfBirth = "1990-05-15",
                bloodGroup = "O+",
                allergies = listOf("Penicillin", "Peanuts"),
                biometricEnabled = true,
                notificationsEnabled = true,
                darkModeEnabled = false,
                expiryAlertDays = 30
            )
        )
    }
}

class UpdateProfileUseCase @Inject constructor() {
    suspend operator fun invoke(profile: UserProfile): Result<Unit> {
        // Update profile logic here
        return Result.success(Unit)
    }
}