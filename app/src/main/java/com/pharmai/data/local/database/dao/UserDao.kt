package com.pharmai.data.local.database.dao

import androidx.room.*
import com.pharmai.data.model.database.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String): Flow<UserEntity>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("UPDATE users SET isBiometricEnabled = :enabled WHERE id = :userId")
    suspend fun updateBiometricStatus(userId: String, enabled: Boolean)

    @Query("UPDATE users SET isDarkModeEnabled = :enabled WHERE id = :userId")
    suspend fun updateDarkModeStatus(userId: String, enabled: Boolean)

}