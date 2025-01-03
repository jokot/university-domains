package com.kitabisa.test.universitydomains.core.testing.dao

import com.kitabisa.test.universitydomains.core.database.dao.RecentSearchDao
import com.kitabisa.test.universitydomains.core.database.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestRecentSearchDao : RecentSearchDao {

    private val _recentSearchesFlow = MutableStateFlow<List<RecentSearchEntity>>(emptyList())

    // Test-only method to emit network results
    fun sendDatabaseResult(networkResult: List<String>) {
        _recentSearchesFlow.tryEmit(networkResult.map { RecentSearchEntity(it) })
    }

    override fun getRecentSearches(): Flow<List<RecentSearchEntity>> = _recentSearchesFlow

    override fun insertRecentSearch(recentSearch: RecentSearchEntity) {
        val current = _recentSearchesFlow.value.toMutableList()

        // Replace the search if it already exists or add it otherwise
        val index = current.indexOfFirst { it.name == recentSearch.name }
        if (index >= 0) {
            current[index] = recentSearch
        } else {
            current.add(recentSearch)
        }

        // Sort by timestamp descending
        _recentSearchesFlow.value = current.sortedByDescending { it.timeStamp }
    }

    override fun deleteRecentSearch(recentSearch: RecentSearchEntity) {
        _recentSearchesFlow.update { current ->
            current.filterNot { it.name == recentSearch.name }
        }
    }

    override fun clearRecentSearch() {
        _recentSearchesFlow.value = emptyList()
    }
}
