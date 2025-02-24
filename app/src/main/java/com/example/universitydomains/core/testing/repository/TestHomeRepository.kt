package com.example.universitydomains.core.testing.repository

import com.example.universitydomains.core.data.repository.HomeRepository
import com.example.universitydomains.core.data.state.DataState
import com.example.universitydomains.core.model.SavableUniversity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class TestHomeRepository(
    private val wrappedRepository: HomeRepository
) : HomeRepository {

    override fun getUniversities(): Flow<DataState<List<SavableUniversity>>> {
        return flow {
            wrappedRepository.getUniversities()
                .onStart { emit(DataState.Loading); delay(50) } // Add test-specific delay
                .collect { emit(it) }
        }
    }

    override suspend fun toggleFavorite(savableUniversity: SavableUniversity) {
        wrappedRepository.toggleFavorite(savableUniversity)
    }
}