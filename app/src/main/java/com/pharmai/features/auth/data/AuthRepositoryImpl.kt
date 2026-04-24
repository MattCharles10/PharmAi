package com.pharmai.features.auth.data

import com.pharmai.features.auth.domain.models.User
import com.pharmai.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val user = User(id = "1", email = email, name = "User")
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(name: String, email: String, password: String): Result<User> {
        return try {
            val user = User(id = "1", email = email, name = name)
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        _currentUser.value = null
    }

    override fun getCurrentUser(): Flow<User?> = _currentUser.asStateFlow()

    override suspend fun authenticateWithBiometrics(): Result<Boolean> {
        return Result.success(true)
    }
}