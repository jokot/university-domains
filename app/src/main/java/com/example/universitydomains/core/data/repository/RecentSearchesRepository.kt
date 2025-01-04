package com.example.universitydomains.core.data.repository

import com.example.universitydomains.core.database.dao.RecentSearchDao
import com.example.universitydomains.core.database.di.IoDispatcher
import com.example.universitydomains.core.database.entity.RecentSearchEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RecentSearchesRepository {
    fun getRecentSearch(): Flow<List<String>>
    suspend fun saveRecentSearch(name: String)
    suspend fun removeRecentSearch(name: String)
    suspend fun clearRecentSearch()
}

class RecentSearchesRepositoryImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RecentSearchesRepository {

    private companion object {
        private const val MAX_RECENT_SEARCH = 12
    }

    override fun getRecentSearch(): Flow<List<String>> =
        recentSearchDao.getRecentSearches().map { searches ->
            searches.map { it.name }
        }

    override suspend fun saveRecentSearch(name: String) {
        withContext(dispatcher) {
            recentSearchDao.insertRecentSearch(RecentSearchEntity(name.trim()))
            val recentSearches = recentSearchDao.getRecentSearches().firstOrNull().orEmpty()
            if (recentSearches.size > MAX_RECENT_SEARCH) {
                val toRemove = recentSearches.drop(MAX_RECENT_SEARCH)
                toRemove.forEach { recentSearchDao.deleteRecentSearch(it) }
            }
        }
    }

    override suspend fun removeRecentSearch(name: String) {
        withContext(dispatcher) {
            recentSearchDao.deleteRecentSearch(RecentSearchEntity(name))
        }
    }

    override suspend fun clearRecentSearch() {
        withContext(dispatcher) {
            recentSearchDao.clearRecentSearch()
        }
    }
}
