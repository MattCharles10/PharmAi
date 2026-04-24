package com.pharmai.features.auth.domain.repository

import com.pharmai.features.auth.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signUp(name: String, email: String, password: String): Result<User>
    suspend fun logout()
    fun getCurrentUser(): Flow<User?>
    suspend fun authenticateWithBiometrics(): Result<Boolean>
}