package com.kitabisa.test.universitydomains.core.testing.repository

import com.kitabisa.test.universitydomains.core.data.repository.SearchRepository
import com.kitabisa.test.universitydomains.core.data.state.DataState
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class TestSearchRepository(
    private val wrappedRepository: SearchRepository
) : SearchRepository {

    override fun searchUniversity(name: String): Flow<DataState<List<SavableUniversity>>> {
        return flow {
            wrappedRepository.searchUniversity(name)
                .onStart { emit(DataState.Loading); delay(50) } // Add test-specific delay
                .collect { emit(it) }
        }
    }

    override suspend fun toggleFavorite(savableUniversity: SavableUniversity) {
        wrappedRepository.toggleFavorite(savableUniversity)
    }
}