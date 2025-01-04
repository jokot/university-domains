package com.kitabisa.test.universitydomains.core.data.repository

import com.kitabisa.test.universitydomains.core.data.state.DataState
import com.kitabisa.test.universitydomains.core.database.dao.UniversityDao
import com.kitabisa.test.universitydomains.core.database.di.IoDispatcher
import com.kitabisa.test.universitydomains.core.database.entity.toDomain
import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FavoriteRepository {
    fun getFavoriteUniversities(): Flow<DataState<List<SavableUniversity>>>
    suspend fun toggleFavorite(savableUniversity: SavableUniversity)
}

class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val universityDao: UniversityDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FavoriteRepository {
    override fun getFavoriteUniversities(): Flow<DataState<List<SavableUniversity>>> = flow {
        emit(DataState.Loading)

        localDataSource.getFavorites()
            .map { name ->
                // Convert Set<String> to List<String> and fetch all users at once
                val universities = universityDao.getUniversitiesByNames(name.toList())
                    .map {
                        SavableUniversity(
                            university = it.toDomain(),
                            isFavorite = true
                        )
                    }
                DataState.Success(universities)
            }
            .catch { e ->
                emit(DataState.Error(e.message ?: "Unknown error occurred"))
            }
            .collect { state ->
                emit(state)
            }
    }.flowOn(dispatcher)

    override suspend fun toggleFavorite(savableUniversity: SavableUniversity) {
        withContext(dispatcher) {
            localDataSource.toggleFavorite(
                savableUniversity.university.name,
                savableUniversity.isFavorite
            )
        }
    }
}
