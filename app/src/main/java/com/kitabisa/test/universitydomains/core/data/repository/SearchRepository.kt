package com.kitabisa.test.universitydomains.core.data.repository

import com.kitabisa.test.universitydomains.core.data.state.DataState
import com.kitabisa.test.universitydomains.core.database.dao.UniversityDao
import com.kitabisa.test.universitydomains.core.database.di.IoDispatcher
import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import com.kitabisa.test.universitydomains.core.network.NetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchRepository {
    fun searchUniversity(name: String): Flow<DataState<List<SavableUniversity>>>
    suspend fun toggleFavorite(savableUniversity: SavableUniversity)
}

class SearchRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val universityDao: UniversityDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): SearchRepository{
    override fun searchUniversity(name: String): Flow<DataState<List<SavableUniversity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(savableUniversity: SavableUniversity) {
        TODO("Not yet implemented")
    }

}