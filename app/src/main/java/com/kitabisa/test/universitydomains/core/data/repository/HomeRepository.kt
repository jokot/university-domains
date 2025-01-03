package com.kitabisa.test.universitydomains.core.data.repository

import com.kitabisa.test.universitydomains.core.data.state.DataState
import com.kitabisa.test.universitydomains.core.database.dao.UniversityDao
import com.kitabisa.test.universitydomains.core.database.di.IoDispatcher
import com.kitabisa.test.universitydomains.core.database.entity.toDomain
import com.kitabisa.test.universitydomains.core.database.entity.toEntity
import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import com.kitabisa.test.universitydomains.core.network.NetworkDataSource
import com.kitabisa.test.universitydomains.core.network.NetworkResult
import com.kitabisa.test.universitydomains.core.network.model.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HomeRepository {
    fun getUniversities(): Flow<DataState<List<SavableUniversity>>>
    suspend fun toggleFavorite(savableUniversity: SavableUniversity)
}

class HomeRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val universityDao: UniversityDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : HomeRepository {
    override fun getUniversities(): Flow<DataState<List<SavableUniversity>>> = flow {
        // Emit loading state
        emit(DataState.Loading)

        // Get the fetch status
        val isFetched = localDataSource.getIsFetch().firstOrNull() ?: false

        // Query local database
        val localUniversitiesFlow = universityDao.getUniversities()

        // Collect initial data from the local database
        val initialData = localUniversitiesFlow.firstOrNull()

        if (initialData.isNullOrEmpty() || !isFetched) {
            // If the database is empty, fetch from the network
            when (val networkResult = networkDataSource.getUniversities()) {
                is NetworkResult.Error -> {
                    emit(DataState.Error(networkResult.message))
                    return@flow
                }

                is NetworkResult.Success -> {
                    // Insert fetched data into the database
                    val universities = networkResult.data.map { it.toDomain().toEntity() }
                    universityDao.insertUniversities(universities)

                    localDataSource.setDataIsFetch()
                }
            }
        }

        // Combine data from the local database and favorites
        combine(
            localUniversitiesFlow,
            localDataSource.getFavorites()
        ) { localUniversities, favorites ->
            localUniversities.map { entity ->
                val university = entity.toDomain()
                SavableUniversity(
                    university = university,
                    isFavorite = university.id in favorites
                )
            }
        }.collect { savableUniversities ->
            emit(DataState.Success(savableUniversities))
        }
    }.flowOn(dispatcher)

    override suspend fun toggleFavorite(savableUniversity: SavableUniversity) {
        withContext(dispatcher) {
            localDataSource.toggleFavorite(
                savableUniversity.university.id,
                savableUniversity.isFavorite
            )
        }
    }
}