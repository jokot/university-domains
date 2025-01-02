package com.kitabisa.test.universitydomains.core.data.repository

import com.kitabisa.test.universitydomains.core.data.state.DataState
import com.kitabisa.test.universitydomains.core.database.dao.UniversityDao
import com.kitabisa.test.universitydomains.core.database.di.IoDispatcher
import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoriteRepository {
    fun getFavoriteUniversities(): Flow<DataState<List<SavableUniversity>>>
    suspend fun toggleFavorite(savableUniversity: SavableUniversity)
}

class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val universityDao: UniversityDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): FavoriteRepository {
    override fun getFavoriteUniversities(): Flow<DataState<List<SavableUniversity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(savableUniversity: SavableUniversity) {
        TODO("Not yet implemented")
    }

}
