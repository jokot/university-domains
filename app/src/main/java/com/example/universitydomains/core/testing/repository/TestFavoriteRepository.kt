package com.example.universitydomains.core.testing.repository

import com.example.universitydomains.core.data.repository.FavoriteRepository
import com.example.universitydomains.core.data.state.DataState
import com.example.universitydomains.core.model.SavableUniversity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class TestFavoriteRepository(
    private val wrappedRepository: FavoriteRepository
) : FavoriteRepository {

    override fun getFavoriteUniversities(): Flow<DataState<List<SavableUniversity>>> {
        return flow {
            wrappedRepository.getFavoriteUniversities()
                .onStart { emit(DataState.Loading); delay(50) } // Add test-specific delay
                .collect { emit(it) }
        }
    }

    override suspend fun toggleFavorite(savableUniversity: SavableUniversity) {
        wrappedRepository.toggleFavorite(savableUniversity)
    }
}