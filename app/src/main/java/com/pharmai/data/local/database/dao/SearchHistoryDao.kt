// app/src/main/java/com/pharmai/data/local/database/dao/SearchHistoryDao.kt
package com.pharmai.data.local.database.dao

import androidx.room.*
import com.pharmai.data.model.database.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history WHERE userId = :userId ORDER BY searchedAt DESC LIMIT 20")
    fun getRecentSearches(userId: String): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM search_history WHERE userId = :userId AND `query` LIKE :query ORDER BY searchedAt DESC")
    fun searchHistory(userId: String, query: String): Flow<List<SearchHistoryEntity>>

    @Insert
    suspend fun insertSearch(search: SearchHistoryEntity)

    @Delete
    suspend fun deleteSearch(search: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE userId = :userId")
    suspend fun clearSearchHistory(userId: String)

    @Query("DELETE FROM search_history WHERE userId = :userId AND searchedAt < :cutoffDate")
    suspend fun deleteOldSearches(userId: String, cutoffDate: Date)
}